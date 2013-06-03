package org.jboss.errai.demo.summit2013.client.local;

import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.HTMLPanel;

@Templated
public class ComplaintListWidget extends ListWidget<UserComplaint, ComplaintWidget>
{
   public ComplaintListWidget()
   {}

   public ComplaintListWidget(String type)
   {
      super(new HTMLPanel(type, ""));
   }

   @Override
   protected Class<ComplaintWidget> getItemWidgetType()
   {
      return ComplaintWidget.class;
   }
}
