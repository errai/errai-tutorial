package org.jboss.errai.demo.summit2013.server.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.client.shared.UpdateResponse;

@ApplicationScoped
@Service
public class DataSyncServiceImpl implements DataSyncService {

  @Inject
  private UserComplaintService userComplaintService;
  
  @Inject
  private Event<UserComplaint> updated;

  @Override
  public <X> List<SyncResponse<X>> coldSync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults) {
    List<SyncResponse<X>> response = userComplaintService.sync(dataSet, remoteResults); 

    for (SyncResponse<X> syncRequestResponse : response) {
      if (syncRequestResponse instanceof UpdateResponse) {
        UserComplaint newComplaint = (UserComplaint) ((UpdateResponse) syncRequestResponse).getEntity();
        updated.fire(newComplaint);
      }
    }
    
    return response;
  }
}