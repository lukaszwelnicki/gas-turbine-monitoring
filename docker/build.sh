#!/bin/sh

cd ..
./gradlew clean build
cp api/build/libs/api-*.jar docker/api.jar
cd docker
docker build . -t gas-turbine-monitoring:latest
docker tag gas-turbine-monitoring:latest lukaszwelnicki/gas-turbine-monitoring:latest
docker login
docker push lukaszwelnicki/gas-turbine-monitoring:latest
rm api.jar