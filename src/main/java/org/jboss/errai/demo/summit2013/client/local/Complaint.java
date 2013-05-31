/*
 * Copyright 2009 JBoss, a division of Red Hat Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.errai.demo.summit2013.client.local;

import javax.inject.Inject;

import org.jboss.errai.demo.summit2013.client.shared.UserComplaint;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Model;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

@Page(role = DefaultPage.class)
@Templated("Complaint.html#app-template")
public class Complaint extends Composite
{
   @Inject
   @Model
   private UserComplaint model;

   @Inject
   @DataField
   @Bound
   private TextBox name;

   @Inject
   @DataField
   @Bound
   private TextBox email;

   @Inject
   @DataField
   @Bound
   private TextArea complaint;

   @Inject
   @DataField
   private Button submit;

   @Inject
   @DataField
   private TransitionAnchor<Admin> admin;

   @Inject
   private TransitionTo<ComplaintSubmitted> complaintSubmittedPage;

   @EventHandler("submit")
   private void onSubmit(ClickEvent e)
   {
      Window.alert(model.getComplaint());
      complaintSubmittedPage.go();
   }
}
