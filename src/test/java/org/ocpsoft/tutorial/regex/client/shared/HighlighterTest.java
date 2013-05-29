package org.ocpsoft.tutorial.regex.client.shared;

import org.junit.Assert;
import org.junit.Test;
import org.ocpsoft.tutorial.regex.server.RegexParserImpl;

public class HighlighterTest
{
   private RegexParserImpl l = new RegexParserImpl();

   @Test
   public void testResultsHighlightExplicitGroups() throws Exception
   {
      String text = "the quick brown fox";
      RegexResult result = l.parse(new RegexRequest(text, ".+(quick).+(fo(x))", "$1 $3"));

      String highlighted = new Highlighter().highlight(text, result);
      Assert.assertEquals(
               "the <span style=\"color: #ff1493\" class=\"highlight\">quick</span> brown " +
                        "<span style=\"color: #0099ff\" class=\"highlight\">fo" +
                        "<span style=\"color: #8b008b\" class=\"highlight\">x" +
                        "</span>" +
                        "</span>",
               highlighted);
   }

   @Test
   public void testResultsHighlightImplicitGroups() throws Exception
   {
      String text = "the quick brown fox ";
      RegexResult result = l.parse(new RegexRequest(text, "(\\w+ (\\w+)) ", "$1 $2"));

      String highlighted = new Highlighter().highlight(text, result);
      Assert.assertEquals("<span style=\"color: #ff8c00\" class=\"highlight\">" +
               "<span style=\"color: #daa520\" class=\"highlight\">the " +
               "<span style=\"color: #1e90ff\" class=\"highlight\">quick" +
               "</span></span> </span>" +
               "<span style=\"color: #b22222\" class=\"highlight\">" +
               "<span style=\"color: #0099ff\" class=\"highlight\">brown " +
               "<span style=\"color: #8b008b\" class=\"highlight\">fox" +
               "</span></span> </span>", highlighted);
   }

   @Test
   public void testResultsHighlightOptionalToken() throws Exception
   {
      String text = "x";
      String pattern = "(\\.)?";
      String replacement = "replacement";
      RegexResult result = l.parse(new RegexRequest(text, pattern, replacement));

      String highlighted = new Highlighter().highlight(text, result);
      Assert.assertEquals("<span style=\"color: #ff8c00\" class=\"highlight\"></span>x" +
               "<span style=\"color: #8b008b\" class=\"highlight\"></span>", highlighted);
   }

   @Test
   public void testResultsHighlightOmitsNonCapturingGroups() throws Exception
   {
      String text = "12";
      RegexResult result = l.parse(new RegexRequest(text, "(?=(1))(12)", "$1"));

      Highlighter highlighter = new Highlighter();
      String highlighted = highlighter.highlight(text, result);
      Assert.assertEquals("<span style=\"color: #0099ff\" class=\"highlight\">" +
               "<span style=\"color: #8b008b\" class=\"highlight\">1" +
               "</span>2" +
               "</span>", highlighted);
   }

}
