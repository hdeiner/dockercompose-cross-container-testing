#!/usr/bin/env bash

docker-compose -f docker-compose-cross-container-testing-integration-testing.yml down

rm -rf logs_tomcat *.out* *.properties