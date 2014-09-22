package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class PaperButton extends Button {

  public static PaperButton wrap(com.google.gwt.dom.client.Element element) {

    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);

    PaperButton button = new PaperButton(element);

    // Mark it attached and remember it for cleanup.
    button.onAttach();
    RootPanel.detachOnWindowClose(button);

    return button;
  }

  public PaperButton() {

  }

  public PaperButton(Element elem) {

  }
}
