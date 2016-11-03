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

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.StateSync;
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.TakesValue;

@Templated(value = "contact-page.html#modal-fields", stylesheet = "contact-page.css")
public class ContactEditor implements TakesValue<Contact> {

  @Inject @AutoBound
  private DataBinder<Contact> binder;

  @Inject
  @Bound @DataField
  private PaperInput fullname;

  @Inject
  @Bound @DataField
  private PaperInput nickname;

  @Inject
  @Bound @DataField
  private PaperInput phonenumber;

  @Inject
  @Bound @DataField
  private PaperInput email;

  @Override
  public void setValue(final Contact value) {
    binder.setModel(value);
  }

  @Override
  public Contact getValue() {
    return binder.getModel();
  }

  public void pauseBinding() {
    binder.pause();
  }

  public void resumeBinding() {
    binder.resume(StateSync.FROM_UI);
  }

}
