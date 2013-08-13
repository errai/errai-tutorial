package org.jboss.errai.demo.server;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.jpa.sync.client.shared.DataSyncService;
import org.jboss.errai.jpa.sync.client.shared.JpaAttributeAccessor;
import org.jboss.errai.jpa.sync.client.shared.SyncRequestOperation;
import org.jboss.errai.jpa.sync.client.shared.SyncResponse;
import org.jboss.errai.jpa.sync.client.shared.SyncableDataSet;
import org.jboss.errai.jpa.sync.server.JavaReflectionAttributeAccessor;

/**
 * A stateless EJB implementing the DAO (Data Access Object) pattern for
 * {@link UserComplaint} objects.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserComplaintService {

  /**
   * A JPA EntityManager which is configured according to the
   * {@code forge-default} persistence context defined in
   * {@code /WEB-INF/persistence.xml}. Note that this field is not initialized
   * by the application: it is injected by the EJB container.
   */
  @PersistenceContext(unitName = "forge-default")
  private EntityManager em;

  /**
   * Stores the given new UserComplaint in the database, assigning it a unique ID.
   * When this method returns, the given entity object will have its ID property set.
   * @param entity The UserComplaint instance to store.
   * @throws EntityExistsException If the given UserComplaint is already in the database.
   */
  public void create(UserComplaint entity) {
    em.persist(entity);
  }

  /**
   * Updates the state of the given UserComplaint in the database.
   *
   * @param id The unique identifier for the given UserComplaint.
   * @param entity The UserComplaint to update the database with.
   */
  public void update(Long id, UserComplaint entity) {
    entity.setId(id);
    entity = em.merge(entity);
  }

  /**
   * Removes the UserComplaint with the given ID from the database.
   *
   * @param id
   *          The unique ID of the UserComplaint to delete. Must not be null.
   * @throws IllegalArgumentException
   *           if {@code id} is null, or if there is no UserComplaint with that
   *           ID in the database.
   */
  public void delete(Long id) {
    UserComplaint uc = em.find(UserComplaint.class, id);
    em.remove(uc);
  }

  /**
   * Returns the UserComplaint with the given unique ID.
   *
   * @return The UserComplaint with the given unique ID, or null if there is no
   *         such UserComplaint in the database.
   * @throws IllegalArgumentException
   *           if {@code id} is null.
   */
  public UserComplaint getById(Long id) {
    return em.find(UserComplaint.class, id);
  }

  /**
   * Passes a data sync operation on the given data set to the server-side of
   * the Errai DataSync system.
   * <p>
   * This method is not invoked directly by the application code; it is called
   * via Errai RPC by Errai's ClientSyncManager.
   *
   * @param dataSet
   *          The data set to synchronize.
   * @param remoteResults
   *          The remote results produced by ClientSyncManager, which the
   *          server-side needs to perform to synchronize the server data with
   *          the client data.
   * @return A list of sync response operations that ClientSyncManager needs to
   *         perform to synchronize the client data with the server data.
   */
  public <X> List<SyncResponse<X>> sync(SyncableDataSet<X> dataSet, List<SyncRequestOperation<X>> remoteResults) {
    JpaAttributeAccessor attributeAccessor = new JavaReflectionAttributeAccessor();
    DataSyncService dss = new org.jboss.errai.jpa.sync.server.DataSyncServiceImpl(em, attributeAccessor);
    return dss.coldSync(dataSet, remoteResults);
  }
}
