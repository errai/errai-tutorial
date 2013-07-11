package org.jboss.errai.demo.summit2013.client.local;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.common.client.util.LogUtil;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.demo.summit2013.server.rest.UserComplaintService;
import org.jboss.errai.jpa.sync.client.local.ClientSyncManager;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;

/**
 * An ErraiUI templated page which shows the current set of complaints which have been submitted.
 */
@Page
@Templated("Admin.html#app-template")
public class Admin extends Composite {

  /**
   * The list of complaints that currently exist.
   */
  @DataField
  private ListWidget<UserComplaint, ComplaintListItemWidget> complaints;

  /**
   * The EntityManager that interacts with client-local storage.
   */
  @Inject
  private EntityManager em;

  @Inject
  private App app;

  /**
   * The Errai Data Sync helper class which allows us to initiate a data synchronization with the server.
   */
  @Inject
  private ClientSyncManager syncManager;

  public Admin() {
    complaints = new ComplaintListWidget("tbody");
  }

  /**
   * Queries the list of complaints from the browser's local storage, and
   * displays the results in the {@code complaints} list widget.
   */
  private void loadComplaints() {
    TypedQuery<UserComplaint> query = em.createNamedQuery("allComplaints", UserComplaint.class);
    complaints.setItems(query.getResultList());
  }

  /**
   * Receives UserComplaint objects that are fired as events either on the
   * client or the server.
   * <p>
   * In this application, the {@link UserComplaintService} on the server
   * broadcasts a UserComplaint event to all clients to indicate that somebody
   * just updated that UserComplaint.
   *
   * @param created The UserComplaint object that was just created or updated on the server.
   */
  @SuppressWarnings("unused")
  private void complaintChanged(@Observes UserComplaint created) {
    LogUtil.log("Got complaint from server:" + created);
    try {
      mergeInLocalStorage(created);
      loadComplaints();
    } catch (Throwable t) {
      LogUtil.log(t.getMessage());
    }
  }

  /**
   * Performs a full two-way data synchronization between the client and the
   * server: the server gets all new and updated UserComplaint objects from us,
   * and we get all new and updated UserComplaint objects from the server.
   */
  @PageShown
  private void sync() {
    app.sync(new RemoteCallback<List<SyncResponse<UserComplaint>>>() {
      @Override
      public void callback(List<SyncResponse<UserComplaint>> response) {
        LogUtil.log("Received sync response:" + response);
        loadComplaints();
        flushToLocalStorage();
      }
    });
  }

  private void flushToLocalStorage() {
    // TODO should be done by data sync manager internally
    syncManager.getDesiredStateEm().flush();
    syncManager.getDesiredStateEm().clear();
    syncManager.getExpectedStateEm().flush();
    syncManager.getExpectedStateEm().clear();
  }

  private void mergeInLocalStorage(UserComplaint created) {
    syncManager.getExpectedStateEm().merge(created);
    syncManager.getExpectedStateEm().flush();
    syncManager.getDesiredStateEm().merge(created);
    syncManager.getDesiredStateEm().flush();
  }
}
