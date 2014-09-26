package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperButtonWidget extends ButtonBase {

  public static PaperButtonWidget wrap(Element element) {

    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);

    PaperButtonWidget button = new PaperButtonWidget(element);

    // Mark it attached and remember it for cleanup.
    button.onAttach();
    RootPanel.detachOnWindowClose(button);

    return button;
  }

  public PaperButtonWidget() {
    super(Document.get().createElement(PaperButtonElement.tag));
  }

  public PaperButtonWidget(Element elem) {
    super(elem);
    PaperButtonElement.as(elem);
  }

  public PaperButtonElement getButtonElement() {
    return getElement().cast();
  }

  public void click() {
    getButtonElement().click();
  }
}
