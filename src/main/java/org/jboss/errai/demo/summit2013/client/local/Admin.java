package org.jboss.errai.demo.summit2013.client.local;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaintEndpoint;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;

@Page
@Templated("Admin.html#app-template")
public class Admin extends Composite
{
   @Inject
   private Caller<UserComplaintEndpoint> endpoint;

   @DataField
   private ListWidget<UserComplaint, ComplaintWidget> complaints;

   private static boolean initialized;

   public Admin()
   {
      complaints = new ComplaintListWidget("tbody");
   }

   @AfterInitialization
   private void loadComplaints()
   {
      Admin.initialized = true;
      endpoint.call(new RemoteCallback<List<UserComplaint>>() {
         @Override
         public void callback(List<UserComplaint> userComplaints)
         {
        	Window.alert(userComplaints.toString());
            complaints.setItems(userComplaints);
         }
      }).listAll();
   }

   @SuppressWarnings("unused")
   private void complaintCreated(@Observes UserComplaint created)
   {
      Window.alert("Complaint received!");
      complaints.getValue().add(created);
   }

   @PageShown
   public void refresh()
   {
      if (initialized)
         loadComplaints();
   }
}
