package com.capitaltg.jgroover

import org.junit.After
import org.junit.Before
import org.junit.Test

class JGrooverServerTest {

  SearchHandler handler
  
  @Before
  public void before() {
    handler = new SearchHandler('classpath:/test2.json')
  }
  
  @After
  public void after() {
    
  }
  
  @Test
  public void T01_test() {
    def results = handler.search('$.people[?(@.firstName == "Tobias")]')
    assert results
    results[0].lastName == 'Lazar'
  }
  
  @Test
  public void T02_ignoreCase() {
    def results = handler.search('$.people[?(@.firstName  =~ /TOBIAS/i)]')
    assert results
    results[0].lastName == 'Lazar'
  }
  
  @Test
  public void T03_wildcard() {
    def results = handler.search('$.people[?(@.firstName  =~ /TOBI(.*)/i)]')
    assert results
    results[0].lastName == 'Lazar'
  }
  
}
