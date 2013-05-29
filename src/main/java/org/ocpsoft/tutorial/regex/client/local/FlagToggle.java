package org.ocpsoft.tutorial.regex.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;

public class FlagToggle implements ClickHandler
{

   private static final String DISABLED_STYLE = "disabled";
   private static final String FLAGS_PATTERN = "^\\(\\?([idmsux]*)(-)?([idmsux]*)\\)";
   private Button target;
   private HasText text;
   private String flag;

   private boolean enabled = false;

   public FlagToggle()
   {}

   public FlagToggle(Button target, HasText text, String flag)
   {
      this.target = target;
      this.text = text;
      this.flag = flag;

      refresh();
   }

   public void refresh()
   {
      String value = text.getText();
      enabled = calculateEnabled(value, flag);

      if (enabled)
         target.addStyleName(DISABLED_STYLE);
      else
         target.removeStyleName(DISABLED_STYLE);
   }

   public boolean calculateEnabled(String value, String flag)
   {
      boolean result = false;
      if (value != null && RegExp.compile(FLAGS_PATTERN).test(value))
      {
         MatchResult match;
         RegExp flagsPattern = RegExp.compile(FLAGS_PATTERN);

         if ((match = flagsPattern.exec(value)) != null)
         {
            String on = match.getGroup(1);
            String off = match.getGroup(3);

            if (off != null && off.contains(flag))
               result = false;
            else if (on != null && on.contains(flag))
               result = true;
            else
               result = false;
         }
      }
      else
         result = false;

      return result;
   }

   @Override
   public void onClick(ClickEvent event)
   {
      enabled = !enabled;
      text.setText(updateFlag(text.getText(), flag, enabled));

      if (enabled)
         target.addStyleName(DISABLED_STYLE);
      else
         target.removeStyleName(DISABLED_STYLE);
   }

   public String updateFlag(String value, String flag, boolean enabled)
   {
      if (RegExp.compile(FLAGS_PATTERN).test(value))
      {
         if (enabled && RegExp.compile("^\\(\\?([idmsux]*" + flag + "[idmsux]*)(-)?([idmsux]+)?\\)").test(value))
         {
            value = RegExp.compile(
                     "^\\(\\?([idmsux]*?)(" + flag + ")([idmsux]*)(-?)([idmsux]*?)" + flag + "?([idmsux]*)\\)")
                     .replace(value, "(?$1$2$3$4$5$6)");
         }
         else if (enabled && RegExp.compile("^\\(\\?([idmsux]*)(-)?([idmsux]*)\\)").test(value))
         {
            value = RegExp.compile("^\\(\\?([idmsux]*)(-)?([idmsux]+)?\\)").replace(value, "(?" + flag + "$1$2$3)");
         }
         else if (enabled
                  && RegExp.compile("^\\(\\?([idmsux]*)(-)?(([idmsux]*)(" + flag + ")([idmsux]*))?\\)").test(value))
         {
            value = RegExp.compile(
                     "^\\(\\?([idmsux]*)(-)?(([idmsux]*)(" + flag + ")([idmsux]*))?\\)").replace(value,
                     "(?$1$2$4$6)");
            value = RegExp.compile(
                     "^\\(\\?(([idmsux]*)" + flag + "?([idmsux]*)(-)?)([idmsux]*)\\)").replace(value,
                     "(?" + flag + "$2$3$4)");
         }
         else if (!enabled && RegExp.compile("^\\(\\?([idmsux]?)(-)([idmsux]*" + flag + "[idmsux]*)?\\)").test(value))
         {
            value = RegExp.compile(
                     "^\\(\\?([idmsux]*?)" + flag + "([idmsux]*)(-?)([idmsux]*?)" + flag + "?([idmsux]*)\\)").replace(
                     value, "(?$1$2$3$4$5)");
         }
         else if (!enabled && RegExp.compile("^\\(\\?([idmsux]*" + flag + "[idmsux]*)(-)?([idmsux]+)?\\)").test(value))
         {
            value = RegExp.compile(
                     "^\\(\\?([idmsux]*?)" + flag + "([idmsux]*)(-?)([idmsux]*?)" + flag + "?([idmsux]*)\\)").replace(
                     value, "(?$1$2$3$4$5)");
         }

         if (RegExp.compile("^\\(\\?([idmsux]*)-\\)").test(value))
         {
            value = RegExp.compile("^\\(\\?([idmsux]*)-\\)").replace(value, "(?$1)");
         }

         if (!enabled && RegExp.compile("^\\(\\?-?\\)").test(value))
         {
            value = RegExp.compile("^\\(\\?-?\\)").replace(value, "");
         }
      }

      if (!RegExp.compile(FLAGS_PATTERN).test(value))
      {
         if (enabled)
            value = "(?" + flag + ")" + value;
      }

      return value;
   }
}
