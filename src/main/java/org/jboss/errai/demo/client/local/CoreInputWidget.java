package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class CoreInputWidget extends TextBoxBase {
  private boolean valueChangeHandlerInitialized = false;

  public CoreInputWidget() {
    this(Document.get().createElement(CoreInputElement.TAG));
  }

  protected CoreInputWidget(Element elem, String styleName) {
    super(elem);
    if (styleName != null)
      setStyleName(elem, styleName);
  }

  protected CoreInputWidget(Element elem) {
    super(elem);
    // Assert that the element is attached.
    assert CoreInputElement.as(elem).getType().equalsIgnoreCase("text");
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

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    // Initialization code
    if (!valueChangeHandlerInitialized) {
      valueChangeHandlerInitialized = true;
      addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          ValueChangeEvent.fire(CoreInputWidget.this, getValue());
        }
      });
    }
    return addHandler(handler, ValueChangeEvent.getType());
  }

  private CoreInputElement getCoreElement() {
    return getElement().cast();
  }
}
