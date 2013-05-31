package org.jboss.errai.demo.summit2013.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaintEndpoint;

/**
 * 
 */
@Stateless
public class UserComplaintEndpointImpl implements UserComplaintEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   public Response create(UserComplaint entity)
   {
      em.persist(entity);
      return Response.created(
               UriBuilder.fromResource(UserComplaintEndpoint.class).path(String.valueOf(entity.getId())).build())
               .build();
   }

   public Response deleteById(@PathParam("id") Long id)
   {
      UserComplaint entity = em.find(UserComplaint.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<UserComplaint> findByIdQuery = em.createQuery("SELECT u FROM UserComplaint u WHERE u.id = :entityId",
               UserComplaint.class);
      findByIdQuery.setParameter("entityId", id);
      UserComplaint entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   public List<UserComplaint> listAll()
   {
      final List<UserComplaint> results = em.createQuery("SELECT u FROM UserComplaint u", UserComplaint.class)
               .getResultList();
      return results;
   }

   public Response update(@PathParam("id") Long id, UserComplaint entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}