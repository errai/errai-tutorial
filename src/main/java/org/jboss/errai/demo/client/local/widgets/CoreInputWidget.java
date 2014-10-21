package org.jboss.errai.demo.client.local.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBoxBase;
import org.jboss.errai.demo.client.local.elements.CoreInputElement;

/**
 * Provides a Widget wrapper for the {@link org.jboss.errai.demo.client.local.elements.CoreInputElement}
 * so GWT widget methods can be used on the Polymer element.
 *
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class CoreInputWidget extends TextBoxBase {
  private boolean valueChangeHandlerInitialized = false;

  public CoreInputWidget() {
    this(Document.get().createElement(CoreInputElement.TAG));
  }

  protected CoreInputWidget(Element elem) {
    super(elem);
  }

  public static CoreInputWidget wrap(Element element) {
    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);
    CoreInputWidget inputWidget = new CoreInputWidget(element);

    // Mark it attached and remember it for cleanup.
    inputWidget.onAttach();
    RootPanel.detachOnWindowClose(inputWidget);

    return inputWidget;
  }

  public boolean isEmpty() {
    String value = getValue();
    if((value == null) || (value.isEmpty())) {
      return true;
    }
    return false;
  }

  @Override
  public String getText() {
    return getCoreElement().getInputValue();
  }

  public void setText(String text) {
    setValue(text);
  }

  public String getValue() {
    return getCoreElement().getValue();
  }

  public void setValue(String value) {
    getCoreElement().setValue(value);
  }

  public boolean getMultiline() {
    return getCoreElement().getMultiline();
  }

  public void setMultiline(boolean multiline) {
    getCoreElement().setMultiline(multiline);
  }

  public boolean getRequired() {
    return getCoreElement().getRequired();
  }

  public void setRequired(boolean required) {
    getCoreElement().setRequired(required);
  }

  public String getPlaceholder() {
    return getCoreElement().getPlaceholder();
  }

  public void setPlaceholder(String placeholder) {
    if (placeholder != null)
      getCoreElement().setPlaceholder(placeholder);
  }

  public String getPattern() {
    return getCoreElement().getPattern();
  }

  public void setPattern(String pattern) {
    if (pattern != null)
      getCoreElement().setPattern(pattern);
  }

  public boolean getDisabled() {
    return getCoreElement().getDisabled();
  }

  public void setDisabled(boolean disabled) {
    getCoreElement().setDisabled(disabled);
  }

  public double getMin() {
    return getCoreElement().getMin();
  }

  public void setMin(double min) {
    getCoreElement().setMin(min);
  }

  public double getMax() {
    return getCoreElement().getMax();
  }

  public void setMax(double max) {
    getCoreElement().setMax(max);
  }

  public int getMaxRows() {
    return getCoreElement().getMaxRows();
  }

  public void setMaxRows(int maxRows) {
    getCoreElement().setMaxRows(maxRows);
  }

  public boolean getInvalid() {
    return getCoreElement().getInvalid();
  }

  public void setInvalid(boolean invalid) {
    getCoreElement().setInvalid(invalid);
  }

  public boolean getWillValidate() {
    return getCoreElement().getWillValidate();
  }

  public void setWillValidate(boolean willValidate) {
    getCoreElement().setWillValidate(willValidate);
  }

  public boolean isAutoFocus() {
    return getCoreElement().isAutoFocus();
  }

  public void setAutoFocus(boolean autoFocus) {
    getCoreElement().setAutoFocus(autoFocus);
  }

  private CoreInputElement getCoreElement() {
    Element elem = super.getElement();
    return elem.cast();
  }
}
