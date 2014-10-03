package org.jboss.errai.demo.client.local.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.demo.client.local.elements.PaperInputElement;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperInputWidget extends CoreInputWidget {
  private boolean valueChangeHandlerInitialized = false;

  public PaperInputWidget() {
    this(Document.get().createElement(PaperInputElement.TAG));
  }

  protected PaperInputWidget(Element elem) {
    super(elem);
    // Assert that the element is attached.
    assert PaperInputElement.as(elem).getTagName().equalsIgnoreCase(PaperInputElement.TAG);
  }

  public static PaperInputWidget wrap(Element element) {
    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);
    PaperInputWidget inputWidget = new PaperInputWidget(element);

    // Mark it attached and remember it for cleanup.
    inputWidget.onAttach();
    RootPanel.detachOnWindowClose(inputWidget);

    return inputWidget;
  }

  public String getLabel() {
    return getPaperElement().getLabel();
  }

  public void setLabel(String label) {
    getPaperElement().setLabel(label);
  }

  public String getErrorMessage() {
    return getPaperElement().getErrorMessage();
  }

  public void setErrorMessage(String errorMessage) {
    getPaperElement().setErrorMessage(errorMessage);
  }

  public boolean isFocused() {
    return getPaperElement().isFocused();
  }

  public boolean isPressed() {
    return getPaperElement().isPressed();
  }

  public boolean isFloatingLabel() {
    return getPaperElement().isFloatingLabel();
  }

  public void setFloatingLabel(boolean floatLabel) {
    getPaperElement().setFloatingLabel(floatLabel);
  }


  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    // Initialization code
    if (!valueChangeHandlerInitialized) {
      valueChangeHandlerInitialized = true;
      addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          ValueChangeEvent.fire(PaperInputWidget.this, getValue());
        }
      });
    }
    return addHandler(handler, ValueChangeEvent.getType());
  }


  private PaperInputElement getPaperElement() {
    Element elem = super.getElement();
    return elem.cast();
  }
}
