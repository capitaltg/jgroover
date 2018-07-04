package com.capitaltg.jgroover

import groovy.json.JsonSlurper
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath

class JGrooverServer {

  private Server server
  private SearchHandler handler
  
  static main(args) {
    JGrooverServer server = new JGrooverServer(5050, 'classpath:/test2.json')
    server.setAverageTimeDelay(1000)
    server.errorRate = 0.1
    server.startServer()
  }

  JGrooverServer(int port, def filename) {
    server = new Server(port)
    this.handler = new SearchHandler(filename)
    server.handler = this.handler
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
