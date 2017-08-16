/**
 * Copyright (C) 2016 Red Hat, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.demo.client.local;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLUListElement;
import elemental2.dom.Node;
import org.jboss.errai.demo.client.shared.Contact;
import org.jboss.errai.ui.client.local.api.elemental2.IsElement;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

import static elemental2.dom.DomGlobal.document;

/**
 * <p>
 * An Errai UI component for displaying a bootstrap 3 navigation bar.
 * <p>
 * <p>
 * The HTML markup for this {@link Templated} component is the HTML element with the CSS class {@code navbar} in the
 * file {@code contact-page.html} in this package.
 * <p>
 * <p>
 * The {@link DataField} annotation marks fields that replace HTML elements from the template file. As an example, the
 * field {@link #navbar} is the root {@code nav} element of this component; it can be used to attach this component to
 * the DOM (see {@link AppSetup}).
 * <p>
 * <p>
 * The {@link Bound} annotations mark UI fields as managed by Errai Data Binding, which keeps UI values synchronized
 * with properties in the bound {@link Contact} model instance. (See the base class, {@link BaseContactView}.)
 * <p>
 * <p>
 * Instances of this type should be obtained via Errai IoC, either by using {@link Inject} in another container managed
 * bean, or by programmatic lookup through the bean manager.
 */
@ApplicationScoped
@Templated("contact-page.html#navbar")
public class NavBar implements IsElement {

  @Inject
  @Named("nav")
  @DataField
  private HTMLElement navbar;

  @Inject
  @DataField
  private HTMLUListElement navlist;

  @Override
  public HTMLElement getElement() {
    return navbar;
  }

  public void add(final HTMLElement element) {
    final HTMLElement li = (HTMLElement) document.createElement("li");
    li.appendChild(element);
    navlist.appendChild(li);
  }

  public void remove(final HTMLElement element) {
    Optional.ofNullable(element.parentNode)
            .filter(n -> n.nodeType == Node.ELEMENT_NODE)
            .map(n -> (HTMLElement) n)
            .filter(e -> "li".equalsIgnoreCase(e.tagName))
            .filter(e -> e.parentNode == navlist)
            .ifPresent(e -> navlist.removeChild(e));
  }
}
