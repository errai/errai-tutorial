package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperIconButtonWidget extends PaperButtonWidget {
  public static PaperIconButtonWidget wrap(Element element) {

    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);

    PaperIconButtonWidget button = new PaperIconButtonWidget(element);

    // Mark it attached and remember it for cleanup.
    button.onAttach();
    RootPanel.detachOnWindowClose(button);

    return button;
  }

  public PaperIconButtonWidget() {
    super(Document.get().createElement(PaperIconButtonElement.tag));
  }

  public PaperIconButtonWidget(Element elem) {
    super(elem);
    PaperIconButtonElement.as(elem);
  }

  public PaperIconButtonElement getButtonElement() {
    return getElement().cast();
  }

  public void click() {
    getButtonElement().click();
  }
}
