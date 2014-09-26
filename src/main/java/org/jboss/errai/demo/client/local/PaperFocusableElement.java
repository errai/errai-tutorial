package org.jboss.errai.demo.client.local;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperFocusableElement extends Element {
  protected PaperFocusableElement(){}

  public static PaperFocusableElement as(JavaScriptObject o) {
    assert is(o);
    return (PaperFocusableElement) o;
  }

  public static PaperFocusableElement as(Node node) {
    assert is(node);
    return (PaperFocusableElement) node;
  }

  public static PaperFocusableElement as(Element element) {
    assert is(element);
    return (PaperFocusableElement) element;
  }

  public final native boolean isActive() /*-{
    return this.active;
  }-*/;

  public final native boolean isFocused() /*-{
    return this.focused;
  }-*/;

  public final native boolean isPressed() /*-{
    return this.pressed;
  }-*/;

  public final native boolean isToggled() /*-{
    return this.isToggle;
  }-*/;

  public final native boolean isDisabled() /*-{
    return this.disabled;
  }-*/;

  public final native void setDisabled(boolean disabled) /*-{
    this.disabled = disabled;
  }-*/;
}

