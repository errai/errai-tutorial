package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TagName;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
@TagName(PaperInputElement.TAG)
public class PaperInputElement extends CoreInputElement {
  public static final String TAG = "PAPER-INPUT";

	protected PaperInputElement() {
	}

  public static PaperInputElement as(Element elem) {
//    System.out.println(elem);
		String tagName = elem.getTagName();
//		System.out.println("tagname " + tagName);
		assert tagName.equalsIgnoreCase(TAG);
		return (PaperInputElement) elem;
	}

  public final native String getLabel() /*-{
    return this.label;
  }-*/;

  public final native boolean isFocused() /*-{
    return this.focused;
  }-*/;

  public final native boolean isFloatingLabel() /*-{
    return this.floatingLabel;
  }-*/;

  public final native String getErrorMessage() /*-{
    return this.error;
  }-*/;

  public final native boolean isPressed() /*-{
    return this.pressed;
  }-*/;

  public final native void setLabel(String label) /*-{
    this.label = label;
  }-*/;

  public final native void setFloatingLabel(boolean floatingLabel) /*-{
    this.floatingLabel = floatLabel;
  }-*/;

  public final native void setErrorMessage(String errorMessage) /*-{
    this.error = errorMessage;
  }-*/;

}
