Errai Getting Started Demo
=====================

This simple demo app allows users to file complaints. The user interface is designed using plain HTML5 and CSS. Errai UI enables the injection of UI fields into client side Java classes as well as adding dynamic behaviour to these fields (such as event handlers). The demo also makes use of Errai's two-way data binding and page navigation modules. This can all be seen in `ComplaintForm.java` (which is the companion Java class of the complaint form template defined in `ComplaintForm.html`).

Creating complaints is done using a simple JAX-RS endpoint and Errai's typesafe REST caller support (see the `@EventHandler` method for the submit button in `ComplaintForm.java`). Every time a new complaint is created, the server will fire a CDI event which will be observed by all connected clients (new complaints will automatically appear on all admin pages of all connected clients). The relevant code for firing and observing this CDI event can be found in `UserComplaintEndpointImpl.java` and `Admin.java`.  

The filed complaints are persisted on the server as well as in the browser's offline storage (using Errai JPA), so that admin users can access all complaints while offline. The complaints are automatically kept in sync with the server using Errai's data sync capabilities (see `Admin.java`).

The demo can be packaged as a native mobile app using Errai's Cordova module which also allows for the simple injection of integrated mobile hardware (see the injection of the Camera object in `ComplaintForm.java` for an example).

This demo is designed to work with a full Java EE 7 server such as Wildfly 8. Although it should be possible to craft a deployment of this demo to a simpler web server, it's much simpler to deploy to an EE 7 capable app server.

Prerequisites
-------------

 * Maven 3 (run `mvn --version` on the command line to check)
 * An unzipped copy of Wildfly 8

More detailed instructions can be found in our [Setup Tutorial](tutorial-guide/SETUP.adoc)

Build and deploy (production mode)
----------------

To build a .war file and deploy it to the local running Wildfly instance:

    % mvn clean package wildfly:deploy

Once the above command has completed, you should be able to access the app at the following URL:

    http://localhost:8080/errai-tutorial

More detailed instructions can be found [here](tutorial-guide/RUN.adoc)

Code and Refresh (development mode)
----------------

Using GWT's Super Dev Mode, it is possible to instantly view changes to client-side code by simply refreshing the browser window. 

If you're using the Google Plugin for Eclipse or IntelliJ Ultimate Edition follow [these instructions](http://docs.jboss.org/errai/latest/errai/reference/html_single/#_running_and_debugging_in_your_ide_using_gwt_tooling) to start development mode.

Alternatively, you should be able to start the demo in development mode with this single command:

    % mvn clean gwt:run

When the GWT Dev Mode window opens, press "Launch Default Browser" to start the app. You can now debug client-side code directly in the browser using source maps (make sure source maps are enabled in your browser). Alternatively, you can also configure a debug environment for Eclipse by installing

- the Google Plugin for Eclipse: https://developers.google.com/eclipse/docs/download
- the SDBG plugin: http://sdbg.github.io/

To debug server-side code, set up a remote debugger on port 8001.
Then:
* Run `mvn clean gwt:debug`
* Start your server remote debugger
* Start your client remote debugger
* Press "Launch Default Browser"

See our development guide [here](tutorial-guide/DEVELOP.adoc) for more instructions on setting up dev mode and other details.

Build and deploy as native app to your mobile device
-----------------------------------------------------

The native application will have to know the absolute path to your server or cluster. For this demo you will have to change the REST and Errai Bus endpoint URL in Config.java and App.java (see comments) before building the native application.

To produce the native applications run:

    % mvn clean package -Pmobile,jboss7 -Dplatform=android

After that you can simply install the native app on your phone or tablet. 

e.g for Android run:

    % adb install target/template/platforms/android/bin/HelloCordova-debug.apk

Troubleshooting
---------------

Here are some resources that may help if you encounter difficulties:
* [FAQ](tutorial-guide/FAQ.adoc)
* [Forum](https://community.jboss.org/en/errai)
* IRC : #errai @ freenode
