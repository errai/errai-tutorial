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
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;

import com.google.gwt.user.client.TakesValue;

/**
 * A base class for Errai UI components that present {@link Contact Contacts}.
 *
 * This class has an {@link AutoBound @AutoBound} {@link DataBinder} so that UI components in subclasses annotated with
 * {@link Bound @Bound} are kept in sync with the assigned {@link Contact} model properties via Errai Data-Binding.
 */
public class ContactPresenter implements TakesValue<Contact> {

  @Inject
  @AutoBound
  protected DataBinder<Contact> binder;

  @Override
  public Contact getValue() {
    return binder.getModel();
  }

  @Override
  public void setValue(final Contact model) {
    binder.setModel(model);
  }

}