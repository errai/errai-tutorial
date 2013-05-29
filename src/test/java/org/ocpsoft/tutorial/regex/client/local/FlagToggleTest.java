package org.ocpsoft.tutorial.regex.client.local;

import junit.framework.Assert;

import org.junit.Test;

public class FlagToggleTest
{
   
   @Test
   public void testCalculateEnabled() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals(true, toggle.calculateEnabled("(?i)", "i"));
      Assert.assertEquals(true, toggle.calculateEnabled("(?di)", "i"));
      Assert.assertEquals(true, toggle.calculateEnabled("(?dim)", "i"));
      Assert.assertEquals(false, toggle.calculateEnabled("(?i-i)", "i"));
      Assert.assertEquals(true, toggle.calculateEnabled("(?id-i)", "d"));
   }


   @Test
   public void testUpdateEnableNegated() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)", toggle.updateFlag("(?i-i)", "i", true));
   }


   @Test
   public void testUpdateEnableNegatedMulti() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i-d)", toggle.updateFlag("(?i-id)", "i", true));
   }

   @Test
   public void testUpdateRemoveSingle() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("", toggle.updateFlag("(?i)", "i", false));
   }


   @Test
   public void testUpdateRemoveNegated() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("", toggle.updateFlag("(?i-i)", "i", false));
   }

   @Test
   public void testUpdateRemoveNegatedMulti() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?d)", toggle.updateFlag("(?id-i)", "i", false));
   }

   @Test
   public void testUpdateRemoveMultiNegated() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?-d)", toggle.updateFlag("(?i-id)", "i", false));
   }

   @Test
   public void testUpdateRemoveSingleWithText() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("asdf", toggle.updateFlag("(?i)asdf", "i", false));
   }

   @Test
   public void testUpdateRemoveSinglePrefixNoop() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("asdf(?i)", toggle.updateFlag("asdf(?i)", "i", false));
   }

   @Test
   public void testUpdateRemoveSingleMiddleNoop() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("asdf(?i)1234", toggle.updateFlag("asdf(?i)1234", "i", false));
   }

   @Test
   public void testUpdateRemoveFromMany() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?d)", toggle.updateFlag("(?id)", "i", false));
   }

   @Test
   public void testUpdateRemoveFromManyWithPattern() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?d)asdf", toggle.updateFlag("(?id)asdf", "i", false));
   }

   @Test
   public void testUpdateRemoveFromManyPrefixNoop() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("asdf(?id)", toggle.updateFlag("asdf(?id)", "i", false));
   }

   @Test
   public void testUpdateRemoveFromManyWithPatternMiddleNoop() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("qwer(?id)asdf", toggle.updateFlag("qwer(?id)asdf", "i", false));
   }

   @Test
   public void testUpdateRemoveFromManyPrefixMultiple() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("asdf(?id)", toggle.updateFlag("(?i)asdf(?id)", "i", false));
   }

   @Test
   public void testUpdateRemoveFromManyWithPatternMiddleMultiple() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?d)qwer(?id)asdf", toggle.updateFlag("(?id)qwer(?id)asdf", "i", false));
   }

   @Test
   public void testUpdateAddSingle() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)", toggle.updateFlag("", "i", true));
   }

   @Test
   public void testUpdateAddSingleWithPrefixSpace() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i) (?i)", toggle.updateFlag(" (?i)", "i", true));
   }

   @Test
   public void testUpdateRemoveSingleWithPrefixSpace() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals(" (?i)", toggle.updateFlag(" (?i)", "i", false));
   }

   @Test
   public void testUpdateAddSingleWithText() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)asdf", toggle.updateFlag("asdf", "i", true));
   }

   @Test
   public void testUpdateAddExisting() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?id)", toggle.updateFlag("(?d)", "i", true));
   }

   @Test
   public void testUpdateAddExistingMutliple() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?idm)(.*)", toggle.updateFlag("(?dm)(.*)", "i", true));
   }

   @Test
   public void testUpdateAddEndFromManyWithPattern() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)asdf(?d)", toggle.updateFlag("asdf(?d)", "i", true));
   }

   @Test
   public void testUpdateAddMiddleRemoveFromManyPrefix() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)asdf(?d)1234", toggle.updateFlag("asdf(?d)1234", "i", true));
   }

   @Test
   public void testUpdateAddExistingFromManyWithPatternMiddle() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)qwer(?d)asdf(?id)", toggle.updateFlag("qwer(?d)asdf(?id)", "i", true));
   }

   @Test
   public void testUpdateAddFromManyPrefixMultipleAddsToFirst() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?im)asdf(?d)", toggle.updateFlag("(?m)asdf(?d)", "i", true));
   }

   @Test
   public void testUpdateAddIgnoresCapturingGroupLocalConfig() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)(?d:x)", toggle.updateFlag("(?d:x)", "i", true));
   }

   @Test
   public void testUpdateAddIgnoresCapturingGroupLocalConfigPrefix() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?i)xxbs(?d:x)", toggle.updateFlag("xxbs(?d:x)", "i", true));
   }

   @Test
   public void testUpdateAddIgnoresCapturingGroupLocalConfigSuffix() throws Exception
   {
      FlagToggle toggle = new FlagToggle();
      Assert.assertEquals("(?d)xxbs(?d:x)(?i)", toggle.updateFlag("xxbs(?d:x)(?i)", "d", true));
   }
}
