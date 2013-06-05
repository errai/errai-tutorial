package org.jboss.errai.demo.summit2013.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.JpaAttributeAccessor;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation.Type;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.server.JavaReflectionAttributeAccessor;

@Stateless
@Service
public class DataSyncServiceImpl implements DataSyncService {

  @PersistenceContext(unitName = "forge-default")
  private EntityManager em;

  @Inject
  private Event<UserComplaint> updated;

  @Override
  public <X> List<SyncResponse<X>> coldSync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults) {
    System.out.println("COLD SYNC CALLED:" + remoteResults);
    JpaAttributeAccessor attributeAccessor = new JavaReflectionAttributeAccessor();
    DataSyncService dss = new org.jboss.errai.jpa.sync.server.DataSyncServiceImpl(em, attributeAccessor);

    List<SyncResponse<X>> result = dss.coldSync(dataSet, remoteResults);
    
    for (SyncRequestOperation<X> syncRequestOperation : remoteResults) {
      if (syncRequestOperation.getType() == Type.UPDATED) {
        System.out.println("Firing UPDATE event:" + (UserComplaint) syncRequestOperation.getEntity());
        updated.fire((UserComplaint) syncRequestOperation.getEntity());
      }
    }

    System.out.println("COLD SYNC RETURNS");
    return result;
  }
}