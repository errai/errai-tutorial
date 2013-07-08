package org.jboss.errai.demo.summit2013.client.local;

import javax.inject.Inject;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;

@Page
@Templated("ComplaintSubmitted.html#app-template")
public class ComplaintSubmitted extends Composite
{
   @Inject
   @DataField
   private TransitionAnchor<Admin> admin;

   @Inject
   @DataField
   private TransitionAnchor<ComplaintForm> entry;
}
