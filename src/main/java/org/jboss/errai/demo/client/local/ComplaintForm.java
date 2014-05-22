package org.jboss.errai.demo.client.local;

import javax.inject.Inject;

import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Model;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

@Page(role = DefaultPage.class)
@Templated("ComplaintForm.html#app-template")
public class ComplaintForm extends Composite {

  @Inject @Model
  private UserComplaint complaint;

  @Inject @Bound @DataField
  private TextBox name;

  @Inject @Bound @DataField
  private TextBox email;

  @Inject @Bound @DataField
  private TextArea text;

  @Inject @DataField
  private Button submit;

  @EventHandler("submit")
  private void onSubmit(ClickEvent e) {
    Window.alert(complaint.toString());
  }
}