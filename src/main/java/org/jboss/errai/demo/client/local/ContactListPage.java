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

import static org.jboss.errai.demo.client.shared.Operation.OperationType.CREATE;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.DELETE;
import static org.jboss.errai.demo.client.shared.Operation.OperationType.UPDATE;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.dom.DOMUtil;
import org.jboss.errai.common.client.dom.Anchor;
import org.jboss.errai.common.client.dom.Button;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.function.Function;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.StateSync;
import org.jboss.errai.databinding.client.components.ListComponent;
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.demo.client.shared.ContactOperation;
import org.jboss.errai.demo.client.shared.ContactStorageService;
import org.jboss.errai.demo.client.shared.Operation;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageHiding;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.SinkNative;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Event;

/**
 * <p>
 * An Errai UI component for creating, displaying, updating, and deleting {@link Contact Contacts}. This component is
 * also an Errai Navigation {@link Page}; it will be displayed on the GWT host page whenever the navigation URL fragment
 * is {@code #ContactList}.
 *
 * <p>
 * The HTML markup for this {@link Templated} component is the HTML element with the CSS class {@code contact-list} in
 * the file {@code contact-page.html} in this package. This component uses CSS from the file {@code contact-page.css} in
 * this package.
 *
 * <p>
 * The {@link DataField} annotation marks fields that replace HTML elements from the template file. As an example, the
 * field {@link ContactDisplay#editor} replaces the {@code <div>} element in the template with the CSS class
 * {@code modal-content}. Because {@link ContactEditor} is an Errai UI component, the markup for {@link ContactEditor}
 * will replace the contents of the {@code modal-content} div in this component.
 *
 * <p>
 * This component uses {@link ContactList} to display a list of {@link Contact Contacts}. The {@code List<Contact>}
 * returned by calling {@link DataBinder#getModel()} on {@link #binder} is a model bound to a table of
 * {@link ContactDisplay ContactDisplays} in an HTML table. Any changes to the model list (such as adding or removing
 * items) will be automatically reflected in the displayed table. See {@link ContactList} for more details.
 *
 * <p>
 * Instances of this type should be obtained via Errai IoC, either by using {@link Inject} in another container managed
 * bean, or by programmatic lookup through the bean manager.
 */
@Page(role = DefaultPage.class)
@Templated(value = "contact-page.html#contact-list", stylesheet = "contact-page.css")
public class ContactListPage {

  @Inject
  @AutoBound
  private DataBinder<List<Contact>> binder;

  /* By binding to "this", the ContactList is kept in sync with the list from binder. */
  @Inject
  @Bound(property="this")
  @DataField
  private ListComponent<Contact, ContactDisplay> list;

  @Inject
  @DataField
  private Div modal;

  @Inject
  @DataField("modal-fields")
  private ContactEditor editor;

  @Inject
  @DataField("modal-delete")
  private Button delete;

  @Inject
  private NavBar navbar;

  @Inject
  private Anchor newContactAnchor;

  @Inject
  private Anchor sortContactsAnchor;

  /**
   * This is a simple interface for calling a remote HTTP service. Behind this interface, Errai has generated an HTTP
   * request to the service defined by {@link ContactStorageService} (a JaxRS service).
   */
  @Inject
  private Caller<ContactStorageService> contactService;

  @Inject
  private ClientMessageBus bus;

  @Inject
  private Logger logger;

  /**
   * Register handlers and populate the list of {@link Contact Contacts}.
   */
  @PostConstruct
  private void setup() {
    /*
     * Triggers an HTTP request to the ContactStorageService. The call back will be invoked asynchronously to display
     * all retrieved contacts.
     */
    contactService.call((List<Contact> contacts) -> binder.getModel().addAll(contacts)).getAllContacts();

    // Remove placeholder table row from template.
    DOMUtil.removeAllElementChildren(list.getElement());

    list.setSelector(display -> display.setSelected(true));
    list.setDeselector(display -> display.setSelected(false));

    newContactAnchor.setHref("javascript:");
    newContactAnchor.setTextContent("Create Contact");
    newContactAnchor.setOnclick(e -> displayFormWithNewContact());

    sortContactsAnchor.setHref("javascript:");
    sortContactsAnchor.setTextContent("Sort By Nickname");
    sortContactsAnchor.setOnclick(e -> sortContactsByName());
  }

  @PageShown
  public void addNavBarButtons() {
    navbar.add(newContactAnchor);
    navbar.add(sortContactsAnchor);
  }

  @PageHiding
  public void removeNavBarButtons() {
    navbar.remove(newContactAnchor);
    navbar.remove(sortContactsAnchor);
  }

  /**
   * This is called in response to Errai CDI {@link javax.enterprise.event.Event Events} fired from the server when a
   * new {@link Contact} is created. In this way we can display newly created contacts from other browser sessions.
   */
  public void onRemoteCreated(final @Observes @Operation(CREATE) ContactOperation contactOperation) {
    if (sourceIsNotThisClient(contactOperation)) {
      binder.getModel().add(contactOperation.getContact());
    }
  }

  /**
   * This is called in response to Errai CDI {@link javax.enterprise.event.Event Events} fired from the server when an
   * existing {@link Contact} is updated. In this way we can display new property values for contacts when they are
   * updated from other browser sessions.
   */
  public void onRemoteUpdated(final @Observes @Operation(UPDATE) ContactOperation contactOperation) {
    if (sourceIsNotThisClient(contactOperation)) {
      final int indexOf = binder.getModel().indexOf(contactOperation.getContact());
      if (indexOf == -1) {
        logger.warn("Received update before creation for " + contactOperation.getContact() + " from " + contactOperation.getSourceQueueSessionId());
        binder.getModel().add(contactOperation.getContact());
      } else {
        binder.getModel().set(indexOf, contactOperation.getContact());
      }
    }
  }

  /**
   * This is called in response to Errai CDI {@link javax.enterprise.event.Event Events} fired from the server when an
   * existing {@link Contact} is deleted. In this way we can remove displayed contacts when they are deleted in other
   * browser sessions.
   */
  public void onRemoteDelete(final @Observes @Operation(DELETE) Long id) {
    final Iterator<Contact> contactIter = binder.getModel().iterator();
    while (contactIter.hasNext()) {
      if (id.equals(contactIter.next().getId())) {
        contactIter.remove();
        break;
      }
    }
  }

  /**
   * This is an Errai UI native event handler. The element for which this handler is regsitered is in this class's HTML
   * template file and has the {@code new-content} CSS class.
   * <p>
   * Because there is no {@code new-content} {@link DataField} in this class, this method's parameter is a non-specific
   * {@link Event} (rather than a more specific {@link ClickEvent}). For the same reason, the {@link SinkNative}
   * annotation is required to specify which kinds of DOM events this method should handle.
   * <p>
   * This method displays the hidden modal form so that a user can create a new {@link Contact}.
   */
  @SinkNative(Event.ONCLICK)
  @EventHandler("new-contact")
  public void onNewContactClick(final Event event) {
    displayFormWithNewContact();
  }

  private void displayFormWithNewContact() {
    editor.setValue(new Contact());
    displayModal(false);
  }

  /**
   * This is an Errai UI native event handler. The element for which this handler is regsitered is in this class's HTML
   * template file and has the {@code modal-submit} CSS class.
   * <p>
   * Because there is no {@code modal-submit} {@link DataField} in this class, this method's parameter is a non-specific
   * {@link Event} (rather than a more specific {@link ClickEvent}). For the same reason, the {@link SinkNative}
   * annotation is required to specify which kinds of DOM events this method should handle.
   * <p>
   * This method displays and persists changes made to a {@link Contact} in the {@link ContactEditor}, whether it is a
   * newly created or an previously existing {@link Contact}.
   */
  @SinkNative(Event.ONCLICK)
  @EventHandler("modal-submit")
  public void onModalSubmitClick(final Event event) {
    DOMUtil.removeCSSClass(modal, "displayed");
    if (binder.getModel().contains(editor.getValue())) {
      updateContactFromEditor();
    }
    else {
      createNewContactFromEditor();
    }
  }

  private void createNewContactFromEditor() {
    final Contact editorModel = editor.getValue();
    // Adding this model to the list will create and display a new, bound ContactDisplay in the table.
    binder.getModel().add(editorModel);
    contactService.call((ResponseCallback) response -> {
      // Set the id if we successfully create this contact.
      if (response.getStatusCode() == Response.SC_CREATED) {
        final String createdUri = response.getHeader("Location");
        final String idString = createdUri.substring(createdUri.lastIndexOf('/')+1);
        final long id = Long.parseLong(idString);
        editorModel.setId(id);
      }
    }).create(new ContactOperation(editorModel, bus.getSessionId()));
  }

  private void updateContactFromEditor() {
    /*
     * When editting a contact we paused binding so that changes from the UI do not propogate to the model until
     * "submit" is clicked. This call updates the model with all changes made in the UI while binding was paused.
     */
    editor.syncStateFromUI();
    contactService.call().update(new ContactOperation(editor.getValue(), bus.getSessionId()));
  }

  /**
   * This is an Errai UI native event handler. The element for which this handler is regsitered is in this class's HTML
   * template file and has the {@code modal-cancel} CSS class.
   * <p>
   * Because there is no {@code modal-cancel} {@link DataField} in this class, this method's parameter is a non-specific
   * {@link Event} (rather than a more specific {@link ClickEvent}). For the same reason, the {@link SinkNative}
   * annotation is required to specify which kinds of DOM events this method should handle.
   * <p>
   * This method hides the ContactEditor modal form and resets the bound model.
   */
  @SinkNative(Event.ONCLICK)
  @EventHandler("modal-cancel")
  public void onModalCancelClick(final Event event) {
    DOMUtil.removeCSSClass(modal, "displayed");
  }

  /**
   * This is an Errai UI native event handler. The element for which this handler is regsitered is in this class's HTML
   * template file and has the {@code modal-delete} CSS class.
   * <p>
   * Because there is a {@code modal-delete} {@link DataField} in this class, this method's parameter indicates that
   * this handles click events by accepting {@link ClickEvent} as its parameter.
   * <p>
   * This method removes a {@link Contact} from the displayed table and makes an HTTP request to delete the contact from
   * persistent storage on the server.
   */
  @EventHandler("modal-delete")
  public void onModalDeleteClick(final ClickEvent event) {
    if (binder.getModel().contains(editor.getValue())) {
      final Contact deleted = editor.getValue();
      contactService.call((ResponseCallback) response -> {
        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
          binder.getModel().remove(deleted);
        }
      }).delete(editor.getValue().getId());
      editor.setValue(new Contact());
      DOMUtil.removeCSSClass(modal, "displayed");
    }
  }

  /**
   * Observes local CDI events fired from
   * {@link ContactDisplay#onDoubleClick(com.google.gwt.event.dom.client.DoubleClickEvent)}, in order to display the
   * modal form for editting a contact.
   */
  public void editComponent(final @Observes @DoubleClick ContactDisplay component) {
    selectComponent(component);
    editModel(component.getValue());
  }

  /**
   * This method observes CDI events fired locally by {@link ContactDisplay#onClick(ClickEvent)} in order to highlight a
   * {@link ContactDisplay} when it is clicked.
   */
  public void selectComponent(final @Observes @Click ContactDisplay component) {
    if (list.getSelectedComponents().contains(component)) {
      list.deselectAll();
    }
    else {
      list.deselectAll();
      list.selectComponent(component);
    }
  }

  /**
   * For ignoring remote events that originate from this client.
   */
  private boolean sourceIsNotThisClient(final ContactOperation contactOperation) {
    return contactOperation.getSourceQueueSessionId() == null || !contactOperation.getSourceQueueSessionId().equals(bus.getSessionId());
  }

  /**
   * If the parameter is true then this displays a form for editting (with a delete button). Otherwise show a form for
   * new contacts (no delete button).
   */
  private void displayModal(final boolean showDelete) {
    if (showDelete) {
      delete.getStyle().removeProperty("display");
    } else {
      delete.getStyle().setProperty("display", "none");
    }
    DOMUtil.addCSSClass(modal, "displayed");
  }

  private void editModel(final Contact model) {
    /*
     * This sets the editor model with data-binding paused so that changes to the model are not propogated until the
     * user clicks "submit".
     */
    editor.setValuePaused(model);
    displayModal(true);
  }

  private void sortContactsByName() {
    binder.pause();
    final Function<Contact, String> nickGetter = c -> (c.getNickname() == null ? "" : c.getNickname());
    Collections.sort(binder.getModel(), (a,b) -> {
      return nickGetter.apply(a).compareTo(nickGetter.apply(b));
    });
    binder.resume(StateSync.FROM_MODEL);
  }
}
