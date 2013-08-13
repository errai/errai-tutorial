package org.jboss.errai.demo.server;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.jpa.sync.client.shared.ConflictResponse;
import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.client.shared.UpdateResponse;

/**
 * An Errai RPC service which is called by the client when it wishes to
 * synchronize the complaints data between itself and the server.
 */
@ApplicationScoped
@Service
public class DataSyncServiceImpl implements DataSyncService {

  /**
   * An EJB responsible for getting the JPA EntityManager and for transaction
   * demarcation.
   */
  @Inject
  private UserComplaintService userComplaintService;

  /**
   * A CDI event source that fires UserComplaint instances to observers, both on
   * the server and on clients.
   */
  @Inject
  private Event<UserComplaint> updated;

  @Override
  public <X> List<SyncResponse<X>> coldSync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults) {

    // First, let the UserComplaintService EJB do the sync (it gets the correct
    // EntityManager and also handles transactions)
    List<SyncResponse<X>> response = userComplaintService.sync(dataSet, remoteResults);

    // Now fire a CDI event for each UserComplaint which was updated as a result
    // of this sync
    for (SyncResponse<X> syncRequestResponse : response) {
      if (syncRequestResponse instanceof UpdateResponse) {
        UserComplaint newComplaint = (UserComplaint) ((UpdateResponse<?>) syncRequestResponse).getEntity();
        updated.fire(newComplaint);
      }
      else if (syncRequestResponse instanceof ConflictResponse) {
        UserComplaint newComplaint = (UserComplaint) ((ConflictResponse<?>) syncRequestResponse).getActualNew();
        updated.fire(newComplaint);
      }
    }

    // Finally, return the list of sync responses to the client, whose sync
    // manager will update the client database
    return response;
  }
}