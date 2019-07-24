#!/usr/bin/env bash

figlet -f standard -w 160 "Build, Deploy, and Integration Test"
date

echo "Copy database data into Tomcat container"
docker exec tomcat mkdir data
docker cp data/name.basics.tsv tomcat:data/name.basics.tsv
docker cp data/title.akas.tsv tomcat:data/title.akas.tsv
docker cp data/title.basics.tsv tomcat:data/title.basics.tsv
docker cp data/title.crew.tsv tomcat:data/title.crew.tsv
docker cp data/title.episode.tsv tomcat:data/title.episode.tsv
docker cp data/title.principals.tsv tomcat:data/title.principals.tsv
docker cp data/title.ratings.tsv tomcat:data/title.ratings.tsv

echo "Create database populate testing jar"
mvn -q clean compile package

echo "Copy the jar into the Tomcat container"
docker cp target/dockercompose-cross-container-testing-1.0.jar tomcat:dockercompose-cross-container-testing-1.0.jar

echo "Run inside the Tomcat container"
docker exec tomcat java -cp dockercompose-cross-container-testing-1.0.jar com.deinersoft.PopulateDatabase
