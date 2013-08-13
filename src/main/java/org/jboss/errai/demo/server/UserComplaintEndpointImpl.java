package org.jboss.errai.demo.server;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.demo.client.shared.UserComplaintEndpoint;

/**
 * A stateless EJB implementing the REST endpoint to create, update and delete {@link UserComplaint}
 * objects.
 */
@Stateless
public class UserComplaintEndpointImpl implements UserComplaintEndpoint {

  @Inject
  private Event<UserComplaint> created;

  @Inject
  private UserComplaintService complaintService;

  @Override
  public Response create(UserComplaint entity) {
    complaintService.create(entity);
    created.fire(entity);
    return Response.created(
            UriBuilder.fromResource(UserComplaintEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
  }

  @Override
  public Response update(Long id, UserComplaint entity) {
    complaintService.update(id, entity);
    return Response.noContent().build();
  }

  @Override
  public Response delete(Long id) {
    complaintService.delete(id);
    return Response.noContent().build();
  }

}