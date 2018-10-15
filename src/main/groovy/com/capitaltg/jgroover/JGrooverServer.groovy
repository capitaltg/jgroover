package com.capitaltg.jgroover

import org.eclipse.jetty.server.Server

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JGrooverServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(JGrooverServer)

  private static final int DEFAULT_PORT = 5050

  private Server server
  private SearchHandler handler

  static main(args) {
    String config = args ? args[0] : 'classpath:/test2.json'
    int port = args?.length > 1 ? args[1] as Integer : DEFAULT_PORT
    JGrooverServer server = new JGrooverServer(port, config)
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

  def setErrorRate(def errorRate) {
    this.handler.errorRate = errorRate
    return this
  }

  void startServer() {
    server.start()
  }

  def setAverageTimeDelay(def time) {
    handler.averageTimeDelay = time
  }

  void stopServer() {
    server.stop()
  }

}
