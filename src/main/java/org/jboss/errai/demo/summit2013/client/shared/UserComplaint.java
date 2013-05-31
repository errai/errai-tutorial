package org.jboss.errai.demo.summit2013.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

@Bindable
@Portable
public class UserComplaint
{
   private String name;
   private String email;
   private String complaint;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public String getComplaint()
   {
      return complaint;
   }

   public void setComplaint(String complaint)
   {
      this.complaint = complaint;
   }
}
