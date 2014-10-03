package org.jboss.errai.demo.client.local.elements;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TagName;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */

@TagName(CoreInputElement.TAG)
public class CoreInputElement extends InputElement {
  public static final String TAG = "CORE-INPUT";

  protected CoreInputElement() {}

  public final native String getInputValue() /*-{
	return this.inputValue;
  }-*/;

  public final native boolean getRequired() /*-{
    return this.required;
  }-*/;

  public final native void setRequired(boolean required) /*-{
    this.required = required;
  }-*/;

  public final native double getStep() /*-{
    if (this.step == null)
      return 0.0;
    else
      return this.step;
  }-*/;

  public final native void setStep(double step) /*-{
    this.step = step;
  }-*/;

	public final native boolean getMultiline() /*-{
		return this.multiline;
	}-*/;

  public final native void setMultiline(boolean multiline) /*-{
    this.multiline = multiline;
  }-*/;

	public final native String getPattern() /*-{
		return this.pattern;
	}-*/;

  public final native void setPattern(String pattern) /*-{
    this.pattern =  pattern;
  }-*/;

	public final native boolean getDisabled() /*-{
		return this.disabled;
	}-*/;

	public final native double getMin() /*-{
		return this.min;
	}-*/;

  public final native void setMin(double min) /*-{
    this.min = min;
  }-*/;

	public final native double getMax() /*-{
		return this.max;
	}-*/;
  
  public final native void setMax(double max) /*-{
    this.max = max;
  }-*/;
    
	public final native int getMaxRows() /*-{
		return this.maxRows;
	}-*/;
    
  public final native void setMaxRows(int maxRows) /*-{
    this.maxRows =  maxRows;
  }-*/;
  
	public final native boolean getInvalid() /*-{
		return this.invalid;
	}-*/;
  
  public final native void setInvalid(boolean invalid) /*-{
    this.invalid = invalid;
  }-*/;

  public final native boolean getWillValidate() /*-{
    return this.willValidate;
  }-*/;

  public final native void setWillValidate(boolean willValidate) /*-{
    this.willValidate = willValidate;
  }-*/;

  public final native String getPlaceholder() /*-{
    return this.placeholder;
  }-*/;

  public final native void setPlaceholder(String placeholder) /*-{
    this.placeholder = placeholder;
  }-*/;

  public final native boolean isAutoFocus() /*-{
    return this.autofocus;
  }-*/;

  public final native void setAutoFocus(boolean autoFocus) /*-{
    this.autofocus = autoFocus;
  }-*/;

}
