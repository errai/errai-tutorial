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

/**
 * An ErraiUI templated page which shows the current set of complaints which
 * have been submitted.
 */
@Page
@Templated("Admin.html#app-template")
public class Admin extends Composite {

  /**
   * The list of complaints that currently exist.
   */
  @DataField
  private final ListWidget<UserComplaint, ComplaintListItemWidget> complaints;

  /**
   * The EntityManager that interacts with client-local storage.
   */
  @Inject
  private EntityManager em;

  /**
   * The Errai Data Sync helper class which allows us to initiate a data
   * synchronization with the server.
   */
  @Inject
  private ClientSyncManager syncManager;

  @Inject
  private App app;

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
   * In this application, the server broadcasts UserComplaint events to all
   * clients to indicate that somebody just created or updated a UserComplaint.
   * 
   * @param created
   *          The UserComplaint object that was just created or updated on the
   *          server.
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
    loadComplaints();
    
    app.sync(new RemoteCallback<List<SyncResponse<UserComplaint>>>() {
      @Override
      public void callback(List<SyncResponse<UserComplaint>> response) {
        LogUtil.log("Received sync response:" + response);
        loadComplaints();
      }
    });
  }

  /**
   * Manually integrate a server side entity into the client side offline
   * storage. The server fires CDI events for every new or updated
   * {@link UserComplaint}. So, when online we don't have to wait for the next
   * sync request to update this client's "world view". This is essentially just
   * an optimization as the next sync request's response would also contain this
   * {@link UserComplaint} object.
   * 
   * @param userComplaint
   *          the user complaint object that needs to be merged into the client
   *          side data storage.
   */
  public void mergeInLocalStorage(UserComplaint userComplaint) {
    syncManager.getExpectedStateEm().merge(userComplaint);
    syncManager.getExpectedStateEm().flush();
    syncManager.getDesiredStateEm().merge(userComplaint);
    syncManager.getDesiredStateEm().flush();
  }

  /**
   * This method will be invoked when the client is back online after loosing
   * its connection. It triggers synchronization of the local data with the
   * server.
   * 
   * @param onlineEvent
   *          The event object indicating that the client is back online.
   */
  private void online(@Observes OnlineEvent onlineEvent) {
    sync();
  }
}
