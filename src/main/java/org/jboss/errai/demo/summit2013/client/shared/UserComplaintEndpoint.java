package org.jboss.errai.demo.summit2013.client.shared;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/usercomplaints")
public interface UserComplaintEndpoint {
  
  @POST
  @Consumes("application/json")
  public Response create(UserComplaint entity);

  @PUT
  @Path("/{id:[0-9][0-9]*}")
  @Consumes("application/json")
  public Response update(@PathParam("id") Long id, UserComplaint entity);

  @DELETE
  @Path("/{id:[0-9][0-9]*}")
  public Response delete(@PathParam("id") Long id);

}
