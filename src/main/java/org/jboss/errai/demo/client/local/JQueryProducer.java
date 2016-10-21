/*
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

import javax.enterprise.inject.Produces;

import org.jboss.errai.common.client.dom.Element;
import org.jboss.errai.common.client.dom.HTMLElement;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * @author Max Barkley <mbarkley@redhat.com>
 */
@JsType(isNative = true)
public abstract class JQueryProducer {

  @JsFunction @FunctionalInterface
  public static interface JQuery {
    JQueryElement wrap(Element element);
  }

  @JsType(isNative = true)
  public static interface JQueryElement extends HTMLElement {
    void after(HTMLElement element);
    void before(HTMLElement element);
    JQueryArray children();
    JQueryArray children(String selector);
  }

  @JsType(isNative = true)
  public static interface JQueryArray {
    JQueryElement first();
    JQueryElement get(int index);
  }

  @Produces
  @JsProperty(name = "$", namespace = JsPackage.GLOBAL)
  public static native JQuery get();

}
