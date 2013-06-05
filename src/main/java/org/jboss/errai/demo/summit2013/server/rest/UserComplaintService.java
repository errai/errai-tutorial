package org.jboss.errai.demo.summit2013.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.JpaAttributeAccessor;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.server.JavaReflectionAttributeAccessor;

@Stateless
public class UserComplaintService {

  @PersistenceContext(unitName = "forge-default")
  private EntityManager em;
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void create(UserComplaint entity) {
    em.persist(entity);
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void update(Long id, UserComplaint entity) {
    entity.setId(id);
    entity = em.merge(entity);
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void delete(Long id) {
    UserComplaint uc = em.find(UserComplaint.class, id);
    em.remove(uc);
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public UserComplaint getById(Long id) {
    return em.find(UserComplaint.class, id);
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <X> List<SyncResponse<X>> sync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults) {
    JpaAttributeAccessor attributeAccessor = new JavaReflectionAttributeAccessor();
    DataSyncService dss = new org.jboss.errai.jpa.sync.server.DataSyncServiceImpl(em, attributeAccessor);
    return dss.coldSync(dataSet, remoteResults);
  }
}
