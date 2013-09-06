package org.jboss.errai.demo.client.local;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.common.client.util.LogUtil;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.jpa.sync.client.local.ClientSyncManager;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.cordova.events.OnlineEvent;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;

@Page
@Templated("Admin.html#app-template")
public class Admin extends Composite {

  @DataField
  private final ListWidget<UserComplaint, ComplaintListItemWidget> complaints;

  @Inject
  private EntityManager em;

  @Inject
  private ClientSyncManager syncManager;

  @Inject
  private App app;

  public Admin() {
    complaints = new ComplaintListWidget("tbody");
  }

  private void loadComplaints() {
    TypedQuery<UserComplaint> query = em.createNamedQuery("allComplaints", UserComplaint.class);
    complaints.setItems(query.getResultList());
  }

  @PageShown
  private void sync() {
    loadComplaints();
    
    app.sync(new RemoteCallback<List<SyncResponse<UserComplaint>>>() {
      @Override
      public void callback(List<SyncResponse<UserComplaint>> response) {
        LogUtil.log("Received sync response:" + response);
        loadComplaints();
      }
    });
  }

  public void mergeInLocalStorage(UserComplaint userComplaint) {
    syncManager.getExpectedStateEm().merge(userComplaint);
    syncManager.getExpectedStateEm().flush();
    syncManager.getDesiredStateEm().merge(userComplaint);
    syncManager.getDesiredStateEm().flush();
  }

  private void online(@Observes OnlineEvent onlineEvent) {
    sync();
  }
}
