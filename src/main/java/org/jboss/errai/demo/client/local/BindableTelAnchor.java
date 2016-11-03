package org.jboss.errai.demo.client.local;

import org.jboss.errai.common.client.api.annotations.Element;
import org.jboss.errai.common.client.dom.Anchor;
import org.jboss.errai.common.client.ui.HasValue;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

@Element("a")
@JsType(isNative = true)
public interface BindableTelAnchor extends Anchor, HasValue<String> {

  @Override @JsOverlay
  default String getValue() {
    return getTextContent();
  }

  @Override @JsOverlay
  default void setValue(final String value) {
    setTextContent(value);
    setHref("tel:" + value);
  }

}