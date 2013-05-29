package org.ocpsoft.tutorial.regex.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class RegexRequest
{
   private String text;
   private String regex;
   private String replacement;

   public RegexRequest()
   {}

   public RegexRequest(String text, String pattern, String replacement)
   {
      this.text = text;
      this.regex = pattern;
      this.replacement = replacement;
   }

   public String getText()
   {
      return text;
   }

   public String getRegex()
   {
      return regex;
   }

   public String getReplacement()
   {
      return replacement;
   }

   public void setText(String text)
   {
      this.text = text;
   }

   public void setRegex(String regex)
   {
      this.regex = regex;
   }

   public void setReplacement(String replacement)
   {
      this.replacement = replacement;
   }

   @Override
   public String toString()
   {
      return "RegexRequest [text=" + text + ", pattern=" + regex + ", replacement=" + replacement + "]";
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((regex == null) ? 0 : regex.hashCode());
      result = prime * result + ((replacement == null) ? 0 : replacement.hashCode());
      result = prime * result + ((text == null) ? 0 : text.hashCode());
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
      RegexRequest other = (RegexRequest) obj;
      if (regex == null) {
         if (other.regex != null)
            return false;
      }
      else if (!regex.equals(other.regex))
         return false;
      if (replacement == null) {
         if (other.replacement != null)
            return false;
      }
      else if (!replacement.equals(other.replacement))
         return false;
      if (text == null) {
         if (other.text != null)
            return false;
      }
      else if (!text.equals(other.text))
         return false;
      return true;
   }

}
