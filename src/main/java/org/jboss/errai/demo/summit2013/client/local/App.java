package org.jboss.errai.demo.summit2013.client.local;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.common.client.util.LogUtil;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.jpa.sync.client.local.ClientSyncManager;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.ui.cordova.events.OnlineEvent;
import org.jboss.errai.ui.nav.client.local.Navigation;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the entry point to the client portion of the web application. At
 * compile time, Errai finds the {@code @EntryPoint} annotation on this class
 * and generates bootstrap code that creates an instance of this class when the
 * page loads. This client-side bootstrap code will also call the
 * {@link #init()} method because it is annotated with the
 * {@code @PostConstruct} annotation.
 */
@EntryPoint
public class App {

  @Inject
  private Navigation navigation;

  @Inject
  private ClientSyncManager syncManager;

  @PostConstruct
  private void init() {
    // This is specifying the relative path to the REST endpoint used to store
    // complaints on the server. When compiling the native mobile app of this
    // demo, this needs to be changed to an absolute URL.
    RestClient.setApplicationRoot("/errai-summit-2013/rest");
    // RestClient.setApplicationRoot("http://10.15.16.207:8080/errai-summit-2013/rest");

    // Add the navigation panel to make our @Pages visible. The first page that
    // is to be displayed is ComplaintForm, since it is annotated with
    // @Page(role = DefaultPage.class).
    RootPanel.get().add(navigation.getContentPanel());
  }

  @AfterInitialization
  private void afterInit() {
    Admin.init = true;
  }

  public void sync() {
    sync(new RemoteCallback<List<SyncResponse<UserComplaint>>>() {
      @Override
      public void callback(List<SyncResponse<UserComplaint>> response) {
        LogUtil.log("Received sync response:" + response);
      }
    });
  }

  public void sync(RemoteCallback<List<SyncResponse<UserComplaint>>> callback) {
    LogUtil.log("Sending sync:");
    syncManager.coldSync("allComplaints", UserComplaint.class, Collections.<String, Object> emptyMap(), callback, null);
  }

  /**
   * This method will be invoked when the client is back online after loosing
   * its connection. It triggers synchronization of the local data with the
   * server.
   * 
   * @param onlineEvent  The event object indicating that the client is back online. 
   */
  private void online(@Observes OnlineEvent onlineEvent) {
    sync();
  }
}