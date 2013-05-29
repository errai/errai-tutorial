package org.ocpsoft.tutorial.regex.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.marshalling.client.api.annotations.MapsTo;

@Portable
public class Group implements Comparable<Group>
{
   private String fragment;
   private int start;
   private int end;

   public Group(
            @MapsTo("fragment") String fragment,
            @MapsTo("start") int start,
            @MapsTo("end") int end)
   {
      this.fragment = fragment;
      this.start = start;
      this.end = end;
   }

   public String getFragment()
   {
      return fragment;
   }

   public int getStart()
   {
      return start;
   }

   public int getEnd()
   {
      return end;
   }

   @Override
   public String toString()
   {
      return "Group [fragment=" + fragment + ", start=" + start + ", end=" + end + "]";
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + end;
      result = prime * result + start;
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Group other = (Group) obj;
      if (end != other.end)
         return false;
      if (start != other.start)
         return false;
      return true;
   }

   /**
    * @return -1 when this object should be processed before the other
    * 0 when this object is the same as the other
    * 1 when this object should be processed after the other 
    */
   @Override
   public int compareTo(Group o)
   {
      if(o == null)
         return 1;
      
      if(start < o.getStart())
            return 1;
      
      if(start == o.getStart())
      {
         if(end > o.getEnd())
            return 1;
         else if(end < o.getEnd())
            return -1;
      }

      if(start > o.getStart())
      {
         if(end < o.getEnd())
            return -1;
         if(end > o.getEnd())
            return 1;
      }
      
      return 0;
   }
}
