package org.jboss.errai.demo.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.databinding.client.api.PropertyChangeEvent;
import org.jboss.errai.databinding.client.api.PropertyChangeHandler;
import org.jboss.errai.demo.client.shared.UserComplaint;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.client.widget.ValueImage;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;

/**
 * A templated widget that will be used to display a row in a table of
 * {@link UserComplaint}s (see {@link ComplaintListWidget}).
 */
@Templated("Admin.html#complaint")
public class ComplaintListItemWidget extends Composite implements HasModel<UserComplaint> {

  /**
   * Errai's data binding module will automatically bind the provided instance
   * of the model (see {@link #setModel(UserComplaint)}) to all fields annotated
   * with {@link Bound}. If not specified otherwise, the bindings occur based on
   * matching field names (e.g. userComplaint.id will automatically be kept in
   * sync with the data-field "id")
   */
  @Inject
  @AutoBound
  private DataBinder<UserComplaint> userComplaint;

  // You can also choose to instantiate your own widgets. Injection is not
  // required. In case of Element, direct injection is not supported.
  @Bound
  @DataField
  private final Element id = DOM.createTD();

  @Inject
  @Bound
  @DataField
  private CheckBox done;

  @Bound
  @DataField
  private final Element name = DOM.createTD();

  @Bound
  @DataField
  private final Element email = DOM.createTD();

  @Bound
  @DataField
  private final Element text = DOM.createTD();

  @Inject
  @Bound
  @DataField
  private ValueImage image;

  @Bound
  @DataField
  private final Element version = DOM.createTD();

  /**
   * Errai's JPA module allows persisting objects into the browser's offline
   * storage.
   */
  @Inject
  private EntityManager em;

  @Inject
  private App app;

  @PostConstruct
  private void init() {
    // We attach a property change handler to get notified when the user clicks
    // the done checkbox. We can attach the handler to the model directly
    // because it is automatically kept in sync by Errai's data binder.
    userComplaint.addPropertyChangeHandler("done", new PropertyChangeHandler<Boolean>() {
      @Override
      public void onPropertyChange(PropertyChangeEvent<Boolean> event) {
        // Update the style to reflect the change.
        updateDoneStyle();
        // Persist the changed state into the browsers offline storage.
        em.merge(getModel());
        // Attempt to synchronize the change with the server.
        app.sync();
      }
    });
  }

  @Override
  public UserComplaint getModel() {
    return userComplaint.getModel();
  }

  @Override
  public void setModel(UserComplaint model) {
    userComplaint.setModel(model);
    updateDoneStyle();
  }

  /**
   * Updates the CSS style of this row according to the done state of the
   * corresponding {@link UserComplaint}.
   */
  private void updateDoneStyle() {
    if (userComplaint.getModel().isDone()) {
      removeStyleName("issue-open");
      addStyleName("issue-closed");
    }
    else {
      removeStyleName("issue-closed");
      addStyleName("issue-open");
    }
  }
}
