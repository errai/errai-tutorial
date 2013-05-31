package org.jboss.errai.demo.summit2013.client.shared;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

@Entity
@Bindable
@Portable
public class UserComplaint
{
   private String name;
   private String email;
   private String complaint;

   @Lob
   private String image;

   public String getImage()
   {
      return image;
   }

   public void setImage(String image)
   {
      this.image = image;
   }

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
