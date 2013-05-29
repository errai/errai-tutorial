package org.ocpsoft.tutorial.regex.client.shared;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface RegexParser
{
   RegexResult parse(RegexRequest request);
}
