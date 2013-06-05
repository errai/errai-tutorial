package org.jboss.errai.demo.summit2013.server.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaintEndpoint;

@Stateless
public class UserComplaintEndpointImpl implements UserComplaintEndpoint {
  
  @Inject
  private Event<UserComplaint> created;

  @Inject
  private UserComplaintService complaintService;
  
  public Response create(UserComplaint entity) {
    complaintService.create(entity);
    created.fire(entity);
    return Response.created(
            UriBuilder.fromResource(UserComplaintEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
  }
  
  public Response update(Long id, UserComplaint entity) {
    complaintService.update(id, entity);
    return Response.noContent().build();
  }
  
  public Response delete(Long id) {
    complaintService.delete(id);
    return Response.noContent().build();
  }

}