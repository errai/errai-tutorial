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

package org.jboss.errai.demo.client.local;

import static org.jboss.errai.common.client.dom.DOMUtil.removeAllElementChildren;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.CREATE;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.DELETE;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.UPDATE;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.dom.Button;
import org.jboss.errai.common.client.dom.Form;
import org.jboss.errai.common.client.dom.MouseEvent;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.components.ListComponent;
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.demo.client.shared.ContactOperation;
import org.jboss.errai.demo.client.shared.ContactStorageService;
import org.jboss.errai.demo.client.shared.Operation;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.http.client.Response;

@Page(role = DefaultPage.class, path = "/contacts")
@Templated(value = "contact-page.html#contact-list", stylesheet = "contact-page.css")
public class ContactListPage {

  @Inject @DataField
  private Form modal;

  @Inject @DataField("modal-delete")
  private Button delete;

  @Inject @DataField("modal-fields")
  private ContactEditor editor;

  @Inject @AutoBound
  private DataBinder<List<Contact>> binder;

  @Inject @Bound @DataField
  private ListComponent<Contact, ContactDisplay> list;

  @Inject
  private Caller<ContactStorageService> contactService;

  @Inject
  private ClientMessageBus bus;

  @PostConstruct
  private void init() {
    removeAllElementChildren(list.getElement());
    contactService.call((final List<Contact> contacts) -> binder.getModel().addAll(contacts)).getAllContacts();
  }

  @EventHandler("new-contact")
  public void newContact(@ForEvent("click") final MouseEvent event) {
    displayModal(false);
    editor.setValue(new Contact());
  }

  @EventHandler("modal-submit")
  public void submit(@ForEvent("click") final MouseEvent event) {
    if (modal.checkValidity()) {
      hideModal();
      if (!binder.getModel().contains(editor.getValue())) {
        createNewContactFromEditor();
      }
      else {
        editContactFromEditor();
      }
    }
  }

  private void editContactFromEditor() {
    editor.resumeBinding();
    contactService.call().update(new ContactOperation(editor.getValue(), bus.getSessionId()));
  }

  @EventHandler("modal-cancel")
  public void cancel(@ForEvent("click") final MouseEvent event) {
    hideModal();
  }

  @EventHandler("modal-delete")
  public void delete(@ForEvent("click") final MouseEvent event) {
    binder.getModel().remove(editor.getValue());
    contactService.call().delete(editor.getValue().getId());
    hideModal();
  }

  public void editContact(@Observes final ContactDisplay display) {
    editor.setValue(display.getValue());
    editor.pauseBinding();
    displayModal(true);
  }

  public void remoteCreate(@Observes @Operation(CREATE) final ContactOperation operation) {
    if (!operation.getSourceQueueSessionId().equals(bus.getSessionId())) {
      binder.getModel().add(operation.getContact());
    }
  }

  public void remoteUpdate(@Observes @Operation(UPDATE) final ContactOperation operation) {
    if (!operation.getSourceQueueSessionId().equals(bus.getSessionId())) {
      final int index = binder.getModel().indexOf(operation.getContact());
      if (index != -1) {
        binder.getModel().set(index, operation.getContact());
      } else {
        binder.getModel().add(operation.getContact());
      }
    }
  }

  public void remoteDelete(@Observes @Operation(DELETE) final Long id) {
    final Iterator<Contact> iter = binder.getModel().iterator();
    while (iter.hasNext()) {
      if (iter.next().getId() == id) {
        iter.remove();
        break;
      }
    }
  }

  private void createNewContactFromEditor() {
    final Contact newContact = editor.getValue();
    contactService.call((ResponseCallback) response -> {
      if (response.getStatusCode() == Response.SC_CREATED) {
        final String location = response.getHeader("Location");
        final String idString = location.substring(location.lastIndexOf('/')+1);
        final long id = Long.parseLong(idString);
        newContact.setId(id);
        binder.getModel().add(newContact);
      }

    }).create(new ContactOperation(editor.getValue(), bus.getSessionId()));
  }

  private void hideModal() {
    modal.getClassList().remove("displayed");
  }

  private void displayModal(final boolean showDelete) {
    modal.getClassList().add("displayed");
    if (showDelete) {
      delete.getStyle().removeProperty("display");
    } else {
      delete.getStyle().setProperty("display", "none");
    }
  }

}
