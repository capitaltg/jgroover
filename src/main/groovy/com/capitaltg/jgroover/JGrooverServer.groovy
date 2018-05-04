package com.capitaltg.jgroover

import groovy.json.JsonSlurper
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath

class JGrooverServer {

  static main(args) {

    JGrooverServer server = new JGrooverServer()
    server.startServer()
    
  }

  def void startServer() {
    def server = new Server(5050)
    def handler = new ServletContextHandler(ServletContextHandler.SESSIONS)
    server.handler = new SearchHandler()
    server.start()

    def file = new File('test.json')
    def text = file.text
    def json = new JsonSlurper().parseText(text)

    Object document = Configuration.defaultConfiguration().jsonProvider().parse(text);
    def response = JsonPath.read(document, '$.people[?(@.firstName == "Geoffrey")]')
    println response
    
  }
}

