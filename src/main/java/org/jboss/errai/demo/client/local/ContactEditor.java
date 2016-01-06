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

import javax.inject.Inject;

import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.DateInput;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.jboss.errai.common.client.dom.TextArea;
import org.jboss.errai.common.client.dom.TextInput;
import org.jboss.errai.databinding.client.api.StateSync;
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * <p>
 * An Errai UI component for creating and editing a single {@link Contact}. This component can be bound to a
 * {@link Contact} by calling {@link #setValue(Contact)}. It can also copy the state of a {@link Contact} without
 * binding to it (and then later overwrite the state of the copied {@link Contact}).
 *
 * <p>
 * The HTML markup for this {@link Templated} component is the HTML element with the CSS class {@code modal-content} in
 * the file {@code contact-page.html} in this package. This component uses CSS from the file {@code contact-page.css} in
 * this package.
 *
 * <p>
 * The {@link DataField} annotation marks fields that replace HTML elements from the template file. As an example, the
 * field {@link ContactDisplay#root} is the root {@code <div>} element of this component; it can be used to attach this
 * component to the DOM.
 *
 * <p>
 * The {@link Bound} annotations mark UI fields with values that Errai Data-Binding keeps synchronized with properties
 * in the bound {@link Contact} model instance. (See the base class, {@link ContactPresenter}.)
 *
 * <p>
 * Instances of this type should be obtained via Errai IoC, either by using {@link Inject} in another container managed
 * bean, or by programmatic lookup through the bean manager.
 */
@Templated(value = "contact-page.html#modal-fields", stylesheet = "contact-page.css")
public class ContactEditor extends ContactPresenter implements IsElement {

  /**
   * The {@link DataField} annotation for this field declares that this {@link Div} is the element from the
   * template file with the CSS class {@code modal-content}. Because of the fragment {@code #modal-content} in the
   * {@link Templated#value()} on this class, this is the root element of this template.
   */
  @Inject
  @DataField("modal-fields")
  private Div root;

  @Inject
  @Bound @DataField
  private TextInput fullname;

  @Inject
  @Bound @DataField
  private TextInput nickname;

  @Inject
  @Bound @DataField
  private TextInput phonenumber;

  @Inject
  @Bound @DataField
  private TextInput email;

  @Inject
  @Bound @DataField
  private TextArea notes;

  /*
   * We specify a converter because Errai does not provide built-in conversion from String to Date.
   */
  @Inject
  @Bound(converter = DateConverter.class) @DataField
  private DateInput birthday;

  /**
   * Sets the given model as the model for this component but pauses data-binding. Any changes made to the UI or model
   * will not be synchronized until {@link #syncStateFromUI()} is called.
   */
  public void setValuePaused(final Contact model) {
    binder.setModel(model, StateSync.FROM_MODEL);
    binder.pause();
  }

  /**
   * If binding is paused, overwrite the state of the model with the state from the UI.
   */
  public void syncStateFromUI() {
    // Does nothing if already resumed.
    binder.resume(StateSync.FROM_UI);
  }

  @Override
  public HTMLElement getElement() {
    return root;
  }

}
