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

package org.jboss.errai.demo.server;

import static org.jboss.errai.demo.client.shared.Operation.OperationType.CREATE;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.DELETE;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.UPDATE;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.demo.client.shared.ContactOperation;
import org.jboss.errai.demo.client.shared.ContactStorageService;
import org.jboss.errai.demo.client.shared.Operation;

/**
 * Server-side implementation for the RPC service, {@link ContactStorageService}. Performs database CRUD operations
 * using the {@link ContactEntityService} and fires Errai CDI {@link Event Events} that are observed by clients over the
 * wire to publish creation, update, and deletion of {@link Contact Contacts}.
 */
@Stateless
public class ContactStorageServiceImpl implements ContactStorageService {

  @Inject
  private ContactEntityService entityService;

  @Inject
  @Operation(CREATE)
  private Event<ContactOperation> created;

  @Inject
  @Operation(UPDATE)
  private Event<ContactOperation> updated;

  @Inject
  @Operation(DELETE)
  private Event<Long> deleted;

  @Override
  public List<Contact> getAllContacts() {
    return entityService.getAllContacts();
  }

  @Override
  public Response create(final ContactOperation contactOperation) {
    entityService.create(contactOperation.getContact());
    // This event is delivered to call connected clients.
    created.fire(contactOperation);

    return Response.created(UriBuilder.fromResource(ContactStorageService.class)
            .path(String.valueOf(contactOperation.getContact().getId())).build()).build();
  }

  @Override
  public Response update(final ContactOperation contactOperation) {
    entityService.update(contactOperation.getContact());
    // This event is delivered to call connected clients.
    updated.fire(contactOperation);

    return Response.noContent().build();
  }

  @Override
  public Response delete(Long id) {
    entityService.delete(id);
    // This event is delivered to call connected clients.
    deleted.fire(id);

    return Response.noContent().build();
  }

}
