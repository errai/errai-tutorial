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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.jboss.errai.common.client.dom.UnorderedList;
import org.jboss.errai.common.client.dom.Node;
import org.jboss.errai.common.client.function.Optional;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 *
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
