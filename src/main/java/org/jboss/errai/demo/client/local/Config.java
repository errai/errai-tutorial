package org.jboss.errai.demo.client.local;

import org.jboss.errai.bus.client.framework.Configuration;

/**
 * Provides the ability to override the remote location of Errai's message bus.
 * This is needed when compiling to a native app which requires an absolute URL.
 */
public class Config implements Configuration {
  @Override
  public String getRemoteLocation() {
    // When compiling to a native application the absolute path to the server
    // side errai bus endpoint needs to be specified.
    // return "http://10.15.16.207:8080/errai-tutorial/";
    return "";
  }
}
