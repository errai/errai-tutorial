/**
 * Copyright (C) 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.demo.client.shared;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.demo.server.ContactStorageServiceImpl;

/**
 * Defines a JaxRS HTTP service for performing CRUD operations on {@link Contact Contacts}.
 *
 * @see ContactStorageServiceImpl
 */
@Path("/contact")
public interface ContactStorageService {

  /**
   * @return A list of all contacts in this service.
   */
  @GET
  @Produces("application/json")
  List<Contact> getAllContacts();

  /**
   * An HTTP endpoint for creating a new {@link Contact}.
   *
   * @param contactOperation
   *          Contains the {@link Contact} to be created and the {@link ClientMessageBus#getSessionId() queue session
   *          id} of the client creating this contact.
   * @return A {@link Response} with status 201 and a {@code Location} header with the URL for the created contact, if
   *         successful. Otherwise a {@link Response} with an appropriate error status.
   */
  @POST
  @Consumes("application/json")
  Response create(ContactOperation contactOperation);

  /**
   * An HTTP endpoint for updating an existing {@link Contact}.
   *
   * @param contactOperation
   *          Contains the {@link Contact} to be updated and the {@link ClientMessageBus#getSessionId() queue session
   *          id} of the client creating this contact. The id of the contained contact must match an existing contact
   *          from this service.
   * @return A {@link Response} with status 204 if successful. Otherwise a {@link Response} with an appropriate error
   *         status.
   */
  @PUT
  @Consumes("application/json")
  Response update(ContactOperation contactOperation);

  /**
   * @param id
   *          The id number of an existing {@link Contact} to be deleted.
   * @return A {@link Response} with status 204 if successful. Otherwise a {@link Response} with an appropriate error
   *         status.
   */
  @DELETE
  @Path("/{id:[0-9]+}")
  Response delete(@PathParam("id") Long id);

}
