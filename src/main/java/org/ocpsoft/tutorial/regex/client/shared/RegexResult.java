package org.ocpsoft.tutorial.regex.client.shared;

import java.util.ArrayList;
import java.util.List;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class RegexResult
{
   private String error;
   private String replaced;
   private boolean matches;
   private List<Group> groups = new ArrayList<Group>();
   private String text;

   public String getError()
   {
      return error;
   }

   public List<Group> getGroups()
   {
      return groups;
   }

   public String getReplaced()
   {
      return replaced;
   }

   public String getText()
   {
      return text;
   }

   public boolean isMatches()
   {
      return matches;
   }

   public void setError(String error)
   {
      this.error = error;
   }

   public void setMatches(boolean matches)
   {
      this.matches = matches;
   }

   public void setPatternGroups(List<Group> groups)
   {
      this.groups = groups;
   }

   public void setReplaced(String text)
   {
      this.replaced = text;
   }

   public void setText(String text)
   {
      this.text = text;
   }

   @Override
   public String toString()
   {
      return "RegexResult [error=" + error + ", replaced=" + replaced + ", matches=" + matches + "]";
   }
}
