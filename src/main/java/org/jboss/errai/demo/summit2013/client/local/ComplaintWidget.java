package org.jboss.errai.demo.summit2013.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.PropertyChangeEvent;
import org.jboss.errai.databinding.client.api.PropertyChangeHandler;
import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jboss.errai.ui.client.widget.ValueImage;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;

@Templated("Admin.html#complaint")
public class ComplaintWidget extends Composite implements HasModel<UserComplaint>
{
   @Inject
   @AutoBound
   private DataBinder<UserComplaint> userComplaint;

   @Inject
   @Bound
   @DataField
   private CheckBox done;
   @Bound
   @DataField
   private Element name = DOM.createTD();
   @Bound
   @DataField
   private Element email = DOM.createTD();
   @Bound
   @DataField
   private Element complaint = DOM.createTD();
   @Inject
   @Bound
   @DataField
   private ValueImage image;

   @PostConstruct
   private void init()
   {
      userComplaint.addPropertyChangeHandler("done", new PropertyChangeHandler<Boolean>() {
         @Override
         public void onPropertyChange(PropertyChangeEvent<Boolean> event)
         {
            if (event.getNewValue())
            {
               removeStyleName("issue-open");
               addStyleName("issue-closed");
            }
            else
            {
               removeStyleName("issue-closed");
               addStyleName("issue-open");
            }
         }
      });
   }

   @Override
   public UserComplaint getModel()
   {
      return userComplaint.getModel();
   }

   @Override
   public void setModel(UserComplaint model)
   {
      userComplaint.setModel(model);
   }
}
