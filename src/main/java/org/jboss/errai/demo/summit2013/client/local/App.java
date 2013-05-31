package org.jboss.errai.demo.summit2013.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.Navigation;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main application entry point.
 */
@EntryPoint
public class App {
 
  @Inject
  private Navigation navigation;
 
  @PostConstruct
  public void clientMain() {
    RootPanel.get().add(navigation.getContentPanel());
  }
 
}