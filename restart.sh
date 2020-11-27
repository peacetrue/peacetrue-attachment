#!/bin/bash

env=${1-prod}

./gradlew clean
./gradlew bootJar

ptrestart "peacetrue-attachment-app" "8606" "$env"
