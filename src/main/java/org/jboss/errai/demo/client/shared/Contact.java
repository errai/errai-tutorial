/**
 * Copyright (C) 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.demo.client.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * Models a contact in an address book.
 * <p>
 * {@link Bindable} allows Errai Data Binding to synchronize properties from instances in this class with UI components
 * in {@link Templated} Errai UI beans. Properties that can be bound must have getter and setter methods following
 * typical Java naming conventions.
 * <p>
 * {@link Portable} allows instances of this class to be serialized. This allows {@link Contact} instances to be used as
 * parameters or return values of Errai RPC methods. It also allows {@link Contact} instances to be fired and observed
 * as Errai CDI events between client and server.
 * <p>
 * {@link Entity} allows this class to be easily persisted on the server via JPA and {@link NamedQueries} defines a
 * query for looking up all persisted {@link Contact Contacts}.
 */
@Bindable
@Portable
@Entity
@NamedQueries({
  @NamedQuery(name = Contact.ALL_CONTACTS_QUERY, query = "SELECT c FROM Contact c ORDER BY c.id")
})
public class Contact {

  public static final String ALL_CONTACTS_QUERY = "allContacts";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String fullname;

  private String nickname;

  private String phonenumber;

  private String email;

  public String getFullname() {
    return fullname;
  }

  public void setFullname(final String fullname) {
    this.fullname = fullname;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(final String nickname) {
    this.nickname = nickname;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(final String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("[Contact: id=")
           .append(id)
           .append(", nickname=")
           .append(nickname)
           .append(", fullname=")
           .append(fullname)
           .append(", phonenumber=")
           .append(phonenumber)
           .append(", email=")
           .append(email);

    return builder.toString();
  }

  @Override
  public boolean equals(final Object obj) {
    return (obj instanceof Contact) && ((Contact) obj).getId() != 0 && ((Contact) obj).getId() == getId();
  }

  @Override
  public int hashCode() {
    return (int) getId();
  }

}
