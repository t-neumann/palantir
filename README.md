palantir: A JSF front-end for querying genomic count data from RNA-seq experiments and Screens
========================
Author: Tobias Neumann

Technologies: CDI, JSF, Hibernate / JPA, EJB, PrimeFaces

Target Product: War

Source: ([tar](https://github.com/t-neumann/palantir/archive/v0.1.tar.gz), [zip](https://github.com/t-neumann/palantir/archive/v0.1.zip))

What is it?
-----------

It is a deployable Maven 3 project compliant with Java EE6 on [WildFly AS 9.x](http://wildfly.org/downloads/). It includes a persistence unit connecting to a MySQL datasource with processed count data and transformations from experiments such as RNA-seq and Screens. The JSF front-end lets you conveniently browse the data and add metainformation such as sample annotations, screen candidate selection and comments, as well as
grouping data into experiments.  

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on WildFly AS (tested on WildFly 9.x). 

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](https://maven.apache.org/guides/mini/guide-configuring-maven.html) before building and deploying or running any tests.


Start WildFly AS 9 with the Web Profile
-------------------------

1. Open a command line and navigate to the root of the WildFly server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   ./bin/standalone.sh
        For Windows: \bin\standalone.bat
        
Setup MySQL datasource on WildFly AS 9
-------------------------

The application server needs to have a configured JTA MySQL datasource containing the processed data import from [palantir-importer](https://github.com/t-neumann/palantir-importer) as well as the installed MySQL connector.

After setup, configure the datasource in persistence.xml accordingly.
 
Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line._

1. Make sure you have started the WildFly Server as described above.
2. Open a command line and navigate to the root directory of palantir.
3. Type this command to build and deploy the archive:

        mvn clean package org.wildfly.plugins:wildfly-maven-plugin:deploy

4. This will deploy `target/palantir.war` to the running instance of the server.
 

Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/palantir/>.


Undeploy the Archive
--------------------

1. Make sure you have started the Wildfly Server as described above.
2. Open a command line and navigate to the root directory of this palantir.
3. When you are finished testing, type this command to undeploy the archive:

        mvn org.wildfly.plugins:wildfly-maven-plugin:undeploy
        
 Deploy to remote servers
--------------------

* Make sure you have started the Wildfly Server on the remove machine.
* Edit `settings.xml` file in your Maven home (usually `~/.m2/settings.xml`) to contain all relevant remote server credentials

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

	<profiles>
		<profile>
			<id>wildfly-dev</id>
			<properties>
				<wildfly-hostname>dev-server.org</wildfly-hostname>
				<wildfly-port>9990</wildfly-port>
				<wildfly-username>user</wildfly-username>
				<wildfly-password>pw</wildfly-password>
			</properties>
		</profile>

		<profile>
			<id>wildfly-production</id>
			<properties>
				<wildfly-hostname>production-server.org</wildfly-hostname>
				<wildfly-port>9990</wildfly-port>
				<wildfly-username>user</wildfly-username>
				<wildfly-password>pw</wildfly-password>
			</properties>
		</profile>

	</profiles>

</settings>
```

* Deploy the archive to your desired remote machine:

        mvn clean package org.wildfly.plugins:wildfly-maven-plugin:deploy -P wildfly-dev
        mvn clean package org.wildfly.plugins:wildfly-maven-plugin:deploy -P wildfly-production


Run the Arquillian Tests 
-------------------------

Palantir also provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container. 

1. Make sure you have started the WildFly Server as described above.
2. Open a command line and navigate to the root directory of this palantir.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-wildfly-remote


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc
