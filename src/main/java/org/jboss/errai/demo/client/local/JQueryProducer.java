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

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.enterprise.inject.Produces;

/**
 * <p>
 * An example JS interop API for JQuery. Wraps a small subset of JQuery and allows for the JQuery function to be
 * injected.
 *
 * @author Max Barkley <mbarkley@redhat.com>
 */
@JsType(isNative = true)
public abstract class JQueryProducer {

  /**
   * <p>
   * The JQuery function, used to enhance regular elements.
   */
  @JsFunction
  @FunctionalInterface
    public interface JQuery {
    JQueryElement wrap(Element element);
  }

  /**
   * <p>
   * Interface for enhanced JQuery elements, exposing API for some convenient methods for getting children or inserting
   * sibling elements.
   * <p>
   * <p>
   * See {@link AppSetup} for usage.
   *
   * @see AppSetup
   */
  @JsType(isNative = true)
  public abstract class JQueryElement extends HTMLElement {
    abstract void after(HTMLElement element);

    abstract void before(HTMLElement element);

    abstract JQueryArray children();

    abstract JQueryArray children(String selector);
  }

  /**
   * <p>
   * Interface for an element array returned by some {@link JQueryElement} methods.
   */
  @JsType(isNative = true)
  public interface JQueryArray {
    JQueryElement first();

    JQueryElement get(int index);
  }

  /**
   * <p>
   * Declares a producer for the JQuery function, allowing it to be injected via Errai IoC.
   * <p>
   * <p>
   * {@link JsProperty} is used so that GWT translates method calls to property access of the globally-scoped {@code $} symbol.
   */
  @Produces
  @JsProperty(name = "$", namespace = JsPackage.GLOBAL)
  public static native JQuery get();

}
