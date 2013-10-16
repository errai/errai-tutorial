Setup
=====

An Inventory of Tools
---------------------

In this section we will go through how to acquire and use the following tools, which will be necessary for developing Errai and following along with the rest of the tutorial:

* Git

* JDK (6 or 7)

* Maven (3.x)

* JBoss AS (7.1)

* GWT Developer Plugin

* Eclipse IDE (with the M2E plugin)

Although it is possible to use counterparts for some of these tools, we encourage you to use the suggested tools on your first go.

Git
---

Git is a Source Control Management (SCM) software. In this tutorial we will use Git to download the tutorial and eventually to start a new Errai Application.

You can download Git for Mac, Windows, or Linux [here](http://git-scm.com/downloads).

JDK
---

We recommend you use a version 6 or 7 of the Oracle JDK or the OpenJDK.

You can find Oracle's JDK [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

You can find instructions for installing the OpenJDK on several Linux distributions [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

Maven
-----

Maven is a dependency management tool that we will use to build our Errai Application. In order to install and use Maven, you will need to have the JDK installed. It's important to make sure you have version 3 installed. Run `mvn --version` on the command line to check.

Go [here](http://maven.apache.org/download.cgi) to download the latest version of Maven and follow the [installation instructions](http://maven.apache.org/download.cgi#Installation_Instructions).

JBoss AS
--------

JBoss AS is an Application Server. We will use it to host our Errai Application locally while developing. In the tutorial we will be using JBoss 7.1.1, but it should also be possible to use EAP 6 in a similar fashion.

You can download a zip file with JBoss 7.1 [here](http://download.jboss.org/jbossas/7.1/jboss-as-7.1.1.Final/jboss-as-7.1.1.Final.zip). No installation is necessary. Simply unzip it to wherever you would like the application's directory to be located.

GWT Developer Plugin
--------------------

The GWT Developer plugin allows to view your Errai Application in the browser while running in Development Mode.

### For Google Chrome

You can find the plugin for Google Chrome [here](https://chrome.google.com/webstore/detail/gwt-developer-plugin/jpjpnpmbddbjkfaccnmhnkdgjideieim?hl=en).

### For Mozilla Firefox, Internet Explorer, or Safari

The first time you attempt to view a Errai Application in Developer Mode through the browser, you will be prompted with a link to install the plugin.

Eclipse IDE with the M2E Plugin
-------------------------------

Eclipse is an Integrated Development Environment that you can use to develop an Errai Application. It is not necessary to use Eclipse for development, but specific features of Eclipse will be used in parts of this tutorial. The M2E plugin adds support for Maven integration with Eclipse.

If you do not have Eclipse, you can find a version of Eclipse Kepler with the M2E plugin [here](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr1).

If you already have Eclipse installed but do not have the M2E plugin, you can find it in the Eclipse Marketplace (Help > Eclipse Marketplace) by searching for "Maven Integration" or by going [here](http://marketplace.eclipse.org/content/maven-integration-eclipse-juno-and-newer) and dragging the "install" link into your running instance of Eclipse.

What's Next?
------------

Now that you have everything installed, you're ready to try running an Errai Application locally. To find out how, go to next [next section](RUN.md).
