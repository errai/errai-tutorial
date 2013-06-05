package org.jboss.errai.demo.summit2013.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaintEndpoint;

@Stateless
public class UserComplaintEndpointImpl implements UserComplaintEndpoint {
  
  @PersistenceContext(unitName = "forge-default")
  private EntityManager em;

  @Inject
  private Event<UserComplaint> created;

  public Response create(UserComplaint entity) {
    em.persist(entity);
    created.fire(entity);
    return Response.created(
            UriBuilder.fromResource(UserComplaintEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
  }

  public Response deleteById(Long id) {
    UserComplaint entity = em.find(UserComplaint.class, id);
    if (entity == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    em.remove(entity);
    return Response.noContent().build();
  }

  public Response findById(Long id) {
    TypedQuery<UserComplaint> findByIdQuery = em.createQuery("SELECT u FROM UserComplaint u WHERE u.id = :entityId",
            UserComplaint.class);
    findByIdQuery.setParameter("entityId", id);
    UserComplaint entity = findByIdQuery.getSingleResult();
    if (entity == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    return Response.ok(entity).build();
  }

  public List<UserComplaint> listAll() {
    final List<UserComplaint> results = em.createQuery("SELECT u FROM UserComplaint u", UserComplaint.class)
            .getResultList();
    return results;
  }

  public Response update(Long id, UserComplaint entity) {
    entity.setId(id);
    entity = em.merge(entity);
    return Response.noContent().build();
  }

}