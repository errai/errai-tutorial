package org.jboss.errai.demo.client.local;

import javax.inject.Inject;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;

/**
 * The companion Java class of the ComplaintSubmitted template. It represents a
 * simple page that is displayed (transitioned to) after a complaint has been
 * submitted successfully.
 */
@Page
@Templated("ComplaintSubmitted.html#app-template")
public class ComplaintSubmitted extends Composite {
  
  /**
   * A {@link TransitionAnchor} provides special support for using anchor tags
   * in HTML templates. When the anchor is clicked it will automatically
   * transition to the corresponding page.
   */
  @Inject
  @DataField
  private TransitionAnchor<Admin> admin;

  @Inject
  @DataField
  private TransitionAnchor<ComplaintForm> entry;
}
