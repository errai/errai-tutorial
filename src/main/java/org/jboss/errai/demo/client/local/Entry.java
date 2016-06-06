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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.Document;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.slf4j.Logger;

@EntryPoint
public class Entry {

  @Inject
  private Logger logger;

  @Inject
  private Div display;

  @Inject
  private Document doc;

  @Inject
  private EmailAnchor anchorSubtype;

  @PostConstruct
  public void start() {
    doc.getBody().appendChild(display);

    try {
      anchorSubtype.getTextContent();
      println("Successfully accessed textContent propery of anchor subtype.");
    } catch (final Throwable t) {
      final String msg = "Failed to access textContent property of anchor subtype.";
      println(msg + " See console for details.");
      logger.error(msg, t);
    }
  }

  private void println(final String text) {
    final Div line = (Div) doc.createElement("div");
    line.setTextContent(text);
    display.appendChild(line);
  }

}
