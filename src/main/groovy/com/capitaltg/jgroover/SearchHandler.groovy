package com.capitaltg.jgroover

import java.io.IOException

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.PathNotFoundException

/**
 * Handles all server searches.  Looks for APIs with format:
 *   /api/object?param1=value1&param2=value2...
 * and returns results of json search on supplied data file
 */
class SearchHandler extends AbstractHandler {

  def document

  SearchHandler(def filename){
    def file = new File(filename)
    def text = file.text
    this.document = Configuration.defaultConfiguration().jsonProvider().parse(text);
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {

    Request base_request = (request instanceof Request) ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
    def match = request.requestURI =~ /\/api\/(\S*)/
    if(match) {
      def group = match.group(1)
      def string = '$.'+group
      request.parameterMap.each { key, value ->
        (value as List).each { v ->
          string += ("[?(@.$key == \"$v\")]")
        }
      }
      try {
        def results = JsonPath.read(document, string)
        response.setContentType("text/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(results);
      } catch (PathNotFoundException e) {
        // if no object exists in file, ignore that
      }
      base_request.setHandled(true);
      return
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    base_request.setHandled(true);
  }
}
