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

import static org.jboss.errai.common.client.dom.Window.getDocument;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.jboss.errai.common.client.dom.UnorderedList;
import org.jboss.errai.common.client.dom.Node;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * <p>
 * An Errai UI component for displaying a bootstrap 3 navigation bar.
 *
 * <p>
 * The HTML markup for this {@link Templated} component is the HTML element with the CSS class {@code navbar} in the
 * file {@code contact-page.html} in this package.
 *
 * <p>
 * The {@link DataField} annotation marks fields that replace HTML elements from the template file. As an example, the
 * field {@link #navbar} is the root {@code nav} element of this component; it can be used to attach this component to
 * the DOM (see {@link AppSetup}).
 *
 * <p>
 * The {@link Bound} annotations mark UI fields as managed by Errai Data Binding, which keeps UI values synchronized
 * with properties in the bound {@link Contact} model instance. (See the base class, {@link BaseContactView}.)
 *
 * <p>
 * Instances of this type should be obtained via Errai IoC, either by using {@link Inject} in another container managed
 * bean, or by programmatic lookup through the bean manager.
 */
@ApplicationScoped
@Templated("contact-page.html#navbar")
public class NavBar implements IsElement {

  @Inject @Named("nav")
  @DataField
  private HTMLElement navbar;

  @Inject
  @DataField
  private UnorderedList navlist;

  @Override
  public HTMLElement getElement() {
    return navbar;
  }

  public void add(final HTMLElement element) {
    final HTMLElement li = getDocument().createElement("li");
    li.appendChild(element);
    navlist.appendChild(li);
  }

  public void remove(final HTMLElement element) {
    Optional.ofNullable(element.getParentNode())
            .filter(n -> n.getNodeType() == Node.ELEMENT_NODE)
            .map(n -> (HTMLElement) n)
            .filter(e -> "li".equalsIgnoreCase(e.getTagName()))
            .filter(e -> e.getParentNode() == navlist)
            .ifPresent(e -> navlist.removeChild(e));
  }
}
