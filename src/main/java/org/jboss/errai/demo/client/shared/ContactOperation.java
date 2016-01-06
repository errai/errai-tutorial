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

import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * This {@link Portable} type is fired as a CDI event from the server so that clients can observe created and updated
 * {@link Contact Contacts} from different browser sessions without refreshing.
 * <p>
 * This type encapulates a session id as well as a {@link Contact} so that the client that orginally created or updated
 * a {@link Contact} can ignore the event.
 */
@Portable
public class ContactOperation {

  private final Contact contact;
  private final String sourceQueueSessionId;

  public ContactOperation(final @MapsTo("contact") Contact contact,
          final @MapsTo("sourceQueueSessionId") String sourceQueueSessionId) {
    this.contact = contact;
    this.sourceQueueSessionId = sourceQueueSessionId;
  }

  /**
   * A {@link Contact} that has been created or updated.
   */
  public Contact getContact() {
    return contact;
  }

  /**
   * The value of {@link ClientMessageBus#getSessionId()} from the browser session from which the event is caused.
   */
  public String getSourceQueueSessionId() {
    return sourceQueueSessionId;
  }

}
