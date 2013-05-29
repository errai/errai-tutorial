package org.ocpsoft.tutorial.regex.server;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;

import org.jboss.errai.bus.server.annotations.Service;
import org.ocpsoft.tutorial.regex.client.shared.Group;
import org.ocpsoft.tutorial.regex.client.shared.RegexParser;
import org.ocpsoft.tutorial.regex.client.shared.RegexRequest;
import org.ocpsoft.tutorial.regex.client.shared.RegexResult;
import org.ocpsoft.tutorial.regex.server.ParseTools.CaptureFilter;
import org.ocpsoft.tutorial.regex.server.ParseTools.CaptureType;
import org.ocpsoft.tutorial.regex.server.ParseTools.CapturingGroup;

@Service
@RequestScoped
public class RegexParserImpl implements RegexParser
{
   @Override
   public RegexResult parse(RegexRequest request)
   {
      RegexResult result = new RegexResult();
      result.setText(request.getText());

      Matcher matcher = null;

      String replacement = request.getReplacement();
      String regex = request.getRegex();
      if (request.getText() != null && !request.getText().isEmpty())
      {
         try {
            if (regex != null)
            {
               matcher = Pattern.compile(regex).matcher(request.getText());
            }
         }
         catch (Exception e) {
            result.setError(e.getMessage());
         }

         List<CapturingGroup> captures = ParseTools.extractCaptures(CaptureType.PAREN, regex, new CaptureFilter() {
            @Override
            public boolean accept(CapturingGroup group)
            {
               String captured = new String(group.getCaptured());
               return !captured.startsWith("?");
            }
         });

         if (matcher != null)
         {
            matcher.reset();
            if (matcher.matches())
            {
               result.setMatches(matcher.matches());
               result.getGroups().clear();
               for (int i = 0; i < matcher.groupCount(); i++)
               {
                  int start = matcher.start(i + 1);
                  int end = matcher.end(i + 1);
                  if (start != -1 && end != -1)
                     result.getGroups().add(new Group(new String(captures.get(i).getCaptured()), start, end));
               }

               StringBuffer replaced = new StringBuffer();
               if (replacement != null && !replacement.isEmpty()) {
                  matcher.appendReplacement(replaced, request.getReplacement());
                  result.setReplaced(replaced.toString());
               }
            }
            else
            {
               matcher.reset();
               while (matcher.find())
               {
                  Group defaultGroup = new Group(regex, matcher.start(), matcher.end());
                  result.getGroups().add(defaultGroup);
                  for (int i = 0; i < matcher.groupCount(); i++)
                  {
                     int start = matcher.start(i + 1);
                     int end = matcher.end(i + 1);
                     if ((start != -1 && end != -1) &&
                              (defaultGroup.getStart() != start || defaultGroup.getEnd() != end))
                     {
                        result.getGroups().add(new Group(new String(captures.get(i).getCaptured()), start, end));
                     }
                  }
               }

               if (replacement != null && !replacement.isEmpty())
                  result.setReplaced(matcher.replaceAll(replacement));
            }
         }
      }
      return result;
   }

   // TODO add this as an option?
   public String javaMode(String regex)
   {
      StringBuilder result = new StringBuilder();

      int count = 0;
      for (int i = 0; i < regex.length(); i++) {
         char c = regex.charAt(i);

         if (c == '\\')
         {
            count++;
         }

         if (count % 2 == 1)
         {
            if (c != '\\')
            {
               if (i + 1 <= regex.length())
               {
                  throw new RegexException("Unterminated escape sequence at character " + i + ": " + result);
               }
            }
            else if (i + 1 == regex.length())
            {
               throw new RegexException("Unterminated escape sequence at character " + i + ": " + result);
            }
         }
         result.append(c);
      }
      return result.toString().replaceAll("\\\\\\\\", "\\\\");
   }

}