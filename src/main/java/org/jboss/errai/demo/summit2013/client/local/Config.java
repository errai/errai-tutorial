package org.jboss.errai.demo.summit2013.client.local;

import org.jboss.errai.bus.client.framework.Configuration;

/**
 * Overriding the remote location of the server-side bus.
 */
public class Config implements Configuration {
    @Override
    public String getRemoteLocation() {
        //return "http://10.15.16.207:8080/errai-summit-2013/";
    	return "";
    }
}
