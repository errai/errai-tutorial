package org.jboss.errai.demo.client.local.elements;

import com.google.gwt.dom.client.Element;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperButtonElement extends PaperFocusableElement {
  public static String tag = "PAPER-BUTTON";

  protected PaperButtonElement() {
  }

  public static PaperButtonElement as(Element element) {
    PaperFocusableElement elem =  PaperFocusableElement.as(element);
    assert elem.getTagName().equalsIgnoreCase(tag);
    return (PaperButtonElement) elem;
  }

  public final native void click() /*-{
    this.click();
  }-*/;

  public final native String getLabel() /*-{
    return this.label;
  }-*/;

  public final native boolean isRaised() /*-{
    return this.raisedButton;
  }-*/;

  public final native String getIconSource() /*-{
    return this.iconSrc;
  }-*/;

  public final void setIconSource(String iconSrc) {
    if (iconSrc == null)
      throw new NullPointerException("Path to icon cannot be null");

    setIconSrc(iconSrc);
  }

  private native void setIconSrc(String iconPath) /*-{
    this.iconSrc = iconPath;
  }-*/;

  public final native String getIcon() /*-{
    return this.icon;
  }-*/;

  public final void setIcon(String icon) {
    if(icon == null)
      throw new NullPointerException("Path to icon cannot be null");

    setIconName(icon);
  }

  private native void setIconName(String iconName) /*-{
    this.icon
  }-*/;
}
