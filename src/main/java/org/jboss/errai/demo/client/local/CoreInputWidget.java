package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class CoreInputWidget extends TextBoxBase {

  public static CoreInputWidget wrap(Element element) {
    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);

    CoreInputWidget inputWidget = new CoreInputWidget(element);

    // Mark it attached and remember it for cleanup.
    inputWidget.onAttach();
    RootPanel.detachOnWindowClose(inputWidget);

    return inputWidget;
  }

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
  
  @Override
  public String getText() {
    return DOM.getElementProperty(getElement(), "inputValue");
  }
  
  public String getValue() {
    return getCoreElement().getInputValue();
  }

  public CoreInputElement getCoreElement() {
    return getElement().cast();
  }
}
