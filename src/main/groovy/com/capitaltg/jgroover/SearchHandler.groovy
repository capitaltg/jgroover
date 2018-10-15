package com.capitaltg.jgroover

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.PathNotFoundException

import groovy.json.JsonOutput

/**
 * Handles all server searches.  Looks for APIs with format:
 *   /api/object?param1=value1&param2=value2...
 * and returns results of json search on supplied data file
 */
class SearchHandler extends AbstractHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchHandler)

  private Random random = new Random()

  Object document
  long errorRate = 0
  long averageTimeDelay

  SearchHandler(def filename) {
    def text = loadResource(filename)
    this.document = Configuration.defaultConfiguration().jsonProvider().parse(text)
    LOGGER.info("Initialized SearchHandler using $filename")
    this.document.each { k, v -> LOGGER.info("  ${v.size()} $k records") }
  }

  @Override
  void handle(String target, Request request, HttpServletRequest servletRequest, HttpServletResponse response)
  throws IOException, ServletException {

    sleep()

    if (errorRate > Math.random()) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
      request.setHandled(true)
      response.getWriter().println(JsonOutput.toJson([error:'Failed to search JGroover', code:500]))
      return
    }

    def match = servletRequest.requestURI =~ /\/api\/(\S*)/
    if (match) {
      def group = match.group(1)
      def queryString = '$.' + group
      servletRequest.parameterMap.each { key, value ->
        (value as List).each { v ->
          if (v.contains('*')) {
            def v2 = v.replaceAll('\\*', '\\(\\.\\*\\)')
            queryString += ("[?(@.$key =~ /$v2/i)]")
          } else {
            queryString += ("[?(@.$key =~ /$v/i)]")
          }
        }
      }
      try {
        def results = search(queryString)
        response.setContentType("application/json")
        response.setStatus(HttpServletResponse.SC_OK)
        response.getWriter().println(results)
      } catch (PathNotFoundException e) {
        // if no object exists in file, ignore that
      }
      request.setHandled(true)
      return
    }
    LOGGER.warn("Invalid request URI: ${servletRequest.requestURI}")
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
    request.setHandled(true)
  }

  def search(def queryString) {
    try {
      LOGGER.debug("Searching for $queryString")
      def results = JsonPath.read(document, queryString)
      LOGGER.debug("Found ${results.size()} results with total length of ${results.toString().length()} bytes")
      return results
    } catch (PathNotFoundException e) {
      // if no object exists in file, ignore that
    }
  }

  private sleep() {
    if (averageTimeDelay > 0) {
      def time = random.nextInt( 2 * averageTimeDelay)
      Thread.sleep(time)
    }
  }

  private String loadResource(def filename) {
    def match = filename =~ /classpath:(.*)/
    if (match) {
      def resource = match.group(1)
      InputStream input = this.getClass().getResourceAsStream(resource)
      return input.text
    }

    def file = new File(filename)
    return file.text
  }
}
