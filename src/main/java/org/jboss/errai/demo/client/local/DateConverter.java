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

import elemental2.dom.HTMLInputElement;
import org.jboss.errai.databinding.client.api.Bindable;
import org.jboss.errai.databinding.client.api.Converter;

import java.util.Date;

/**
 * Converts between {@link Date} instances used in {@link Bindable} models and {@link String Strings} used in
 * {@link HTMLInputElement} values. Uses Javascript's native {@code Date} object for conversion so that the date
 * {@link String Strings} are properly displayed in input elements with the type "date".
 */
public class DateConverter implements Converter<Date, String> {

  @Override
  public Date toModelValue(final String widgetValue) {
    if (widgetValue == null || widgetValue.equals("")) {
      return null;
    }

    final elemental2.core.Date jsDate = new elemental2.core.Date(widgetValue);
    return new Date((long) jsDate.getTime());
  }

  @Override
  public String toWidgetValue(final Date modelValue) {
    if (modelValue == null) {
      return "";
    } else {
      final elemental2.core.Date jsDate = new elemental2.core.Date(((Long) modelValue.getTime()).doubleValue());
      return jsDate.toISOString().substring(0, 10);
    }
  }

  @Override
  public Class<Date> getModelType() {
    return Date.class;
  }

  @Override
  public Class<String> getComponentType() {
    return String.class;
  }

}
