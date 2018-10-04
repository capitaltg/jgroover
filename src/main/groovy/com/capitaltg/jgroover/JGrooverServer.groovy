package com.capitaltg.jgroover

import groovy.json.JsonSlurper
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath

class JGrooverServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(JGrooverServer)

  private static final int DEFAULT_PORT = 5050;

  private Server server
  private SearchHandler handler
  
  static main(args) {
    String config = args ? args[0] : 'classpath:/test2.json'
    JGrooverServer server = new JGrooverServer(DEFAULT_PORT, config)
    server.setAverageTimeDelay(1000)
    server.errorRate = 0.1
    server.startServer()
  }

  JGrooverServer(int port, def filename) {
    server = new Server(port)
    this.handler = new SearchHandler(filename)
    server.handler = this.handler
    LOGGER.info("Initialized JGrooverServer on port $port, configured to use $filename")
  }

  public setErrorRate(def errorRate) {
    this.handler.errorRate = errorRate
    return this
  }
  
  def void startServer() {
    server.start()
  }

  def setAverageTimeDelay(def time) {
    handler.averageTimeDelay = time
  }
  
  def void stopServer() {
    server.stop()
  }
  
}
