package org.jboss.errai.demo.summit2013.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.Navigation;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main application entry point.
 */
@EntryPoint
public class App
{

   @Inject
   private Navigation navigation;

   @PostConstruct
   public void clientMain()
   {
      RestClient.setApplicationRoot("/errai-summit-2013/rest");
      RootPanel.get().add(navigation.getContentPanel());
   }

}