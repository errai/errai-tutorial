package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Element;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperIconButtonElement extends PaperButtonElement {
  public static String tag = "PAPER-ICON-BUTTON";

  protected PaperIconButtonElement() {

  }

  public static PaperIconButtonElement as(Element element) {
    PaperButtonElement elem = PaperButtonElement.as(element);
    assert elem.getTagName().equalsIgnoreCase(tag);
    return (PaperIconButtonElement) elem;
  }

  public native void setRippleFill(boolean fill) /*-{
    this.fill = fill;
  }-*/;

  public native boolean getRippleFill() /*-{
    return this.fill;
  }-*/;

}
