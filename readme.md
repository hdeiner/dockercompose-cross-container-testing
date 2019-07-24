This project demonstrates the way that Oracle and Tomcat containers can be used by developers to test things like the Incentives applications and that the Tomcat container can talk to the Oracle container using a long form Oracle url in the JDBC connection string.

In terms of what you need to run this project, here's my best guesses:
1. There are no platform dependencies other than a Linux like environment that can execute bash scripts.  I did this development on macOS Mojave, but I'm sure that Ubuntu would work just as well.  I always develop within a virtual machine so I can destroy and recreate my environment when needed should development installs "muck stuff up".  I happen to use the IntelliJ IDE, but that is a personal preference.
2. Docker CE must be installed.
3. The bash scripts make use of figlet to pretty things up. 

Basic theory of operation.  Bash scripts used to implement the workflow of a developer doing incentives development.
1. build_1_run_unit_tests.sh is what is used when unit tests and theire code is being put together.  Since unit tests are, by definition, independent of dependencies such as databases, they can be run and checked quickly many times.
2. build_2_create_oracle_database_image.sh is used to create an Oracle 11g EE Oracle database image.  The idea of pre-baking an image is that there is extraordinary work done by Oracle on first time startup.  By saving this image, we save around 8 minutes when we take the image and create a container from it, which will be done MUCH more often.
3. build_3_create_tomcat_incentives_image is actually not being used right now for much.  The reason for its existence is political.  There is concern that if we were to use the Apache supplied and supported image and create containers from it, deploy wars in it, and save this container as an image, that it would not be viewed as "containerizing" the application.  In the future, this script will probably not be used starting from a Dockerfile, but done by docker commit, saving the container (with its deployed application) as an image.
4. build_4_create_integration_test_environment_using_sqlplus.sh dockercomposes the images for the database and applicaiton server into containers that can talk to one another.  It starts by tearing down any dockercompose currently running, as it is anticipated that developers will use the build_4_create_integration_test_environment anytime a database change is made and build_5_run_integration_tests even more frequently, as code changes are made.  The script creates the dockercompose environment (with a fresh Oracle), inserts the schema and inserts the data.  The other reason for creating our own Tomcat image is that the version of Tomcat used is so old that Apache no longer supports it on DockerHub. 
5. build_5_run_integration_tests creates the final deployment war, deploys the tomcat application, runs a smoke test, and will then run the integration test suite.
6. build_6_destroy_integration_test_environment destroys the containers used, as well as cleans up debris files left around.

```bash
./build_1_run_unit_tests.sh
```
1. NOT FUNCTIONAL YET.  For right now, I just want to demonstrate the cross container communication apects of this architecture.

```bash
./build_2_create_integration_test_environment.sh
```
1. Docker-compose containers for Oracle and Tomcat to life.
2. Wait appropriately for the containers to start and be ready for operations.
3. NOTE: this project used a Docker imaage for Oracle 11g EE called howarddeiner/oracle-11g-ee, which is built in another project.
4. This script took 8.5 minutes to run on my laptop inside a MacOS Mojave virtual machine running inside my MacOS Mojave host.  Based on the logs, the vast najority of the time was spent on Oracle specific and opaque "Creating and starting Oracle instance" and "Completing Database Creation" tasks.

```bash
./build_3_create_tomcat_incentives_image.sh
```
1. This will eventually be where we Dockerfile create Docker images with appropriate wars baked into them.
2. For now, we build a tomcat:7.0.94-jre7 image with custom tomcat-users baked in, so we can use the Tomcat Manager. 

```bash
./build_4_provision_database.sh
```
1. I have used Liquibase to build the database schema.
2. To make this work, I had to change from the ojdbc8.jar to the ojdbc6.jar to avoid the following error:

```bash
Liquibase Update Failed: ORA-00604: error occurred at recursive SQL level 1
ORA-01882: timezone region not found

SEVERE 7/24/19 1:23 PM:liquibase: ORA-00604: error occurred at recursive SQL level 1
ORA-01882: timezone region not found

liquibase.exception.DatabaseException: java.sql.SQLException: ORA-00604: error occurred at recursive SQL level 1
ORA-01882: timezone region not found
```

```bash
./build_5_run_integration_tests.sh
```
1. I have built a custom Java application to populate the database via a REST interface in Tomcat that communicates with the Oracle database, so as to prove container to container communication.  
2. For purposes here, I simply build the jar on the host, copy the jar to the Tomcat container, and then execute it in the Tomcat container.

```bash
./build_6_destroy_integration_test_environment.sh
```
1. Docker-compose away the containers we built.
2. Delete the temporary files we created during the run that facilitated the integration of components.
