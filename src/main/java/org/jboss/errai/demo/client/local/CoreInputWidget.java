package org.jboss.errai.demo.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author Divya Dadlani <ddadlani@redhat.com>
 */
public class CoreInputWidget extends TextBoxBase {
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
		assert CoreInputElement.as(elem).getType().equalsIgnoreCase("text");
	}




}
