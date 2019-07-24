#!/usr/bin/env bash

figlet -f standard -w 180 "Create Oracle and"
figlet -f standard -w 180 "Tomcat Containers"

docker-compose -f docker-compose-cross-container-testing-integration-testing.yml up -d
date

echo "Waiting for Oracle to start"
while true ; do
  curl -s localhost:8081 > tmp.txt
  result=$(grep -c "DOCTYPE HTML PUBLIC" tmp.txt)
  if [ $result = 1 ] ; then
    echo "Oracle has started"
    break
  fi
  sleep 5
done
rm tmp.txt
date

echo "Waiting for Tomcat to start"
while true ; do
  curl -s localhost:8080 > tmp.txt
  result=$(grep -c "Apache Tomcat/7.0.54" tmp.txt)
  if [ $result = 2 ] ; then
    echo "Tomcat has started"
    break
  fi
  sleep 5
done
rm tmp.txt
date

figlet -f slant -w 160 "Load Database Schema Using Liquibase Inside Tomcat Container"
date

echo "Install Liquibase inside Tomcat container"
docker exec tomcat wget https://github.com/downloads/liquibase/liquibase/liquibase-2.0.5-bin.tar.gz
docker exec tomcat gunzip liquibase-2.0.5-bin.tar.gz
docker exec tomcat mkdir /usr/local/liquibase
docker exec tomcat tar -xf liquibase-2.0.5-bin.tar -C /usr/local/liquibase
docker exec tomcat chmod +x /usr/local/liquibase/liquibase
docker cp lib/ojdbc6.jar tomcat:lib/ojdbc6.jar

echo "Build the liquibase.properties file for Liquibase to run against in the Tomcat container"
echo "driver: oracle.jdbc.driver.OracleDriver" > liquibase.properties
echo "classpath: lib/ojdbc6.jar" >> liquibase.properties
echo "url: jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS =(PROTOCOL=TCP)(HOST=oracle)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=EE.oracle.docker)(SERVER=DEDICATED)))" >> liquibase.properties
#echo "url: jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS =(PROTOCOL=TCP)(HOST=localhost)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=EE.oracle.docker)(SERVER=DEDICATED)))" >> liquibase.properties
echo "username: system" >> liquibase.properties
echo "password: oracle" >> liquibase.properties

docker cp liquibase.properties tomcat:liquibase.properties
docker cp src/main/db/IMDB-schema.xml tomcat:IMDB-schema.xml

echo "Run Liquibase inside Tomcat container against Oracle container"
docker exec tomcat /usr/local/liquibase/liquibase --changeLogFile=IMDB-schema.xml update

