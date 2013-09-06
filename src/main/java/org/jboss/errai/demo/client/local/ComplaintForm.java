package org.jboss.errai.demo.client.local;

import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.demo.client.shared.UserComplaintEndpoint;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;
import org.jboss.errai.ui.client.widget.ValueImage;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Model;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.googlecode.gwtphonegap.client.camera.Camera;
import com.googlecode.gwtphonegap.client.camera.PictureCallback;
import com.googlecode.gwtphonegap.client.camera.PictureOptions;

@Page(role = DefaultPage.class)
@Templated("ComplaintForm.html#app-template")
public class ComplaintForm extends Composite {
  
  @Inject
  @Model
  private UserComplaint userComplaint;

  @Inject
  @Bound
  @DataField
  private TextBox name;

  @Inject
  @Bound
  @DataField
  private TextBox email;

  @Inject
  @Bound
  @DataField
  private TextArea complaint;

  @Inject
  @Bound
  @DataField
  private ValueImage image;

  @Inject
  @DataField
  private Button submit;

  @Inject
  private Caller<UserComplaintEndpoint> endpoint;

  @Inject
  private TransitionTo<ComplaintSubmitted> complaintSubmittedPage;

  @Inject
  @DataField
  private TransitionAnchor<Admin> admin;

  @Inject
  private Camera camera;

  @Inject
  @DataField
  private Button takePicture;

  @EventHandler("submit")
  private void onSubmit(ClickEvent e) {
    endpoint.call(new ResponseCallback() {
      @Override
      public void callback(Response response) {
        complaintSubmittedPage.go();
      }
    }).create(userComplaint);
  }

  @EventHandler("takePicture")
  private void onTakePictureClick(ClickEvent e) {
    PictureOptions options = new PictureOptions(25);
    options.setDestinationType(PictureOptions.DESTINATION_TYPE_DATA_URL);
    options.setSourceType(PictureOptions.PICTURE_SOURCE_TYPE_CAMERA);

    camera.getPicture(options, new PictureCallback() {

      @Override
      public void onSuccess(String data) {
        image.setVisible(true);
        image.setValue("data:image/jpeg;base64," + data, true);
      }

      @Override
      public void onFailure(String error) {
        Window.alert("Could not take picture: " + error);
      }
    });
  }
}