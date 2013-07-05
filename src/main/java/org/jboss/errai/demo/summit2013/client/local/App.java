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
 * Main application entry point.
 */
@EntryPoint
public class App {

  @Inject
  private Navigation navigation;

  @Inject
  private ClientSyncManager syncManager;

  @PostConstruct
  private void clientMain() {
    RestClient.setApplicationRoot("/errai-summit-2013/rest");
    // We need to comment this in for the mobile version
    // RestClient.setApplicationRoot("http://10.15.16.207:8080/errai-summit-2013/rest");
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

  @SuppressWarnings("unused")
  private void online(@Observes OnlineEvent onlineEvent) {
    sync();
  }
}