package com.capitaltg.jgroover

import java.io.IOException

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server

class SearchHandler implements Handler {

  @Override
  public void start() throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void stop() throws Exception {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isRunning() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isStarted() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isStarting() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isStopping() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isStopped() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isFailed() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void addLifeCycleListener(Listener listener) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void removeLifeCycleListener(Listener listener) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

        println 1212
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello OneHandler</h1>");
        Request base_request = (request instanceof Request) ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
        base_request.setHandled(true);
  }

  @Override
  public void setServer(Server server) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Server getServer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
    
  }

}
