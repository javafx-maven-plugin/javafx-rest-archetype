JavaFX REST Archetype
=====================

A Maven archetype for generating a basic JavaFX REST client-server starter project using Jetty and SpringFramework.

Usage
======

```
 mvn archetype:generate -DarchetypeGroupId=com.zenjava -DarchetypeArtifactId=javafx-rest-archetype
```

This will generate a multi-module Maven project with three modules:

* **client module**: a simple JavaFX client that makes REST calls onto the server
* **server module**: a simple SpringMVC REST server
* **common module**: common classes shared between client and server (such as data beans)

After creating the project you can build it using Maven commands. From the generated **base project** directory type:

    mvn clean install

Run the server from the base directory of the **server module** using:

    mvn jetty:run

Run the client from the base directory of the **client module** (using `mvn jfx:run` is **deprecated**, because it does not respect launcher-settings).


Development and Deployment
==========================

This is just a quick starter. You most likely will want to open the base level POM in your favorite IDE and develop
your project to your needs.

To build a deployment WAR for your server, run the following from the **base project** directory:

    mvn clean install

And then copy the WAR file from the target directory and deploy this to your web server (e.g. Tomcat).

To build a deployment bundle for your client, run one of the JavaFX distribution mechanisms from the **client module**
base directory, such as:

    mvn clean jfx:jar

Which will build an executable JAR that you can execute(e.g. via double-clicking) to launch your client application.


Licence
============

The JavaFX Basic Archetype provides a Maven archetype for generating a basic JavaFX starter project.

Copyright (C) 2012  Daniel Zwolenski

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses.

