package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TagName;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */

@TagName(CoreInputElement.TAG)
public class CoreInputElement extends InputElement {
	public static final String TAG = "CORE-INPUT";

	protected CoreInputElement() {
	}

	public static CoreInputElement as(Element elem) {
		System.out.println(elem);
		String tagName = elem.getTagName();
		System.out.println("tagname _" + tagName);
		assert tagName.equalsIgnoreCase(TAG);
		return (CoreInputElement) elem;
	}

	public final native boolean getMultiline() /*-{
		return this.multiline;
	}-*/;

	public final native String getPattern() /*-{
		return this.pattern;
	}-*/;

	public final native boolean getDisabled() /*-{
		return this.disabled;
	}-*/;

	public final native boolean getMin() /*-{
		return this.min;
	}-*/;

	public final native boolean getMax() /*-{
		return this.max;
	}-*/;

	public final native int getMaxRows() /*-{
		return this.maxRows;
	}-*/;

	public final native boolean getInvalid() /*-{
		return this.invalid;
	}-*/;

	//public final native String getTagName() /*-{
	//	return this.tagName;
	//}-*/
}
