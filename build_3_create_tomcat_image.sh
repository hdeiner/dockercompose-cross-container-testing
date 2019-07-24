#!/usr/bin/env bash

figlet -f standard -w 160 "Create Tomcat Image"

docker rmi howarddeiner/cross-container-testing-tomcat
docker build src/main/resources/docker-tomcat -t howarddeiner/cross-container-testing-tomcat
