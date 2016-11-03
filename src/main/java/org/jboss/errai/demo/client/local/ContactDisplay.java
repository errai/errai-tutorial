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

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.Heading;
import org.jboss.errai.common.client.dom.MouseEvent;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.ForEvent;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.TakesValue;

@Templated(value = "contact-page.html#contact", stylesheet = "contact-page.css")
public class ContactDisplay implements TakesValue<Contact>, IsElement {

  @Inject
  private Event<ContactDisplay> dblclick;

  @Inject @AutoBound
  private DataBinder<Contact> binder;

  @Inject
  @Bound @DataField
  private Div fullname;

  @Inject @Named("h4")
  @Bound @DataField
  private Heading nickname;

  @Inject
  @Bound @DataField
  private BindableTelAnchor phonenumber;

  @Inject
  @Bound @DataField
  private BindableEmailAnchor email;

  @EventHandler("contact")
  public void onDoubleClick(@ForEvent("dblclick") final MouseEvent event) {
    dblclick.fire(this);
  }

  @Override
  public void setValue(final Contact value) {
    binder.setModel(value);
  }

  @Override
  public Contact getValue() {
    return binder.getModel();
  }

}
