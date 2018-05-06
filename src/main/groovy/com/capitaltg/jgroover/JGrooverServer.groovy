package com.capitaltg.jgroover

import groovy.json.JsonSlurper
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath

class JGrooverServer {

  private int port
  private Server server
  private String filename
  
  static main(args) {
    JGrooverServer server = new JGrooverServer(5050, 'classpath:/test2.json')
    server.startServer()
  }

  JGrooverServer(int port, def filename) {
    this.port = port
    this.filename = filename
  }

  def void startServer() {
    server = new Server(this.port)
    server.handler = new SearchHandler(this.filename)
    server.start()
  }
  
  def void stopServer() {
    server.stop()
  }
  
}

