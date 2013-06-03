package org.jboss.errai.demo.summit2013.client.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Bindable
@Portable
@XmlRootElement
public class UserComplaint
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Version
   private Long version;

   private String name;
   private String email;
   private String complaint;

   private boolean done;

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

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Long getVersion()
   {
      return version;
   }

   public void setVersion(Long version)
   {
      this.version = version;
   }

   public boolean isDone()
   {
      return done;
   }

   public void setDone(boolean done)
   {
      this.done = done;
   }
}
