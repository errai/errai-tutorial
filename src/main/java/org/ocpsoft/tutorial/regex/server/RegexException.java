package org.ocpsoft.tutorial.regex.server;

public class RegexException extends RuntimeException
{
   private static final long serialVersionUID = -6427792994780813623L;

   public RegexException()
   {}

   public RegexException(String message)
   {
      super(message);
   }

   public RegexException(Throwable cause)
   {
      super(cause);
   }

   public RegexException(String message, Throwable cause)
   {
      super(message, cause);
   }

}
