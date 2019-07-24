#!/usr/bin/env bash

figlet -f standard -w 120 "Build And Run Unit Tests"

echo "Build and unit test the application"
mvn -q clean compile test