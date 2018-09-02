#!/bin/sh

cd ..
find . -iname "api-*.jar" -exec cp {} docker/api.jar \;
cd docker
docker build . -t gas-turbine-monitoring:latest
docker tag gas-turbine-monitoring:latest lukaszwelnicki/gas-turbine-monitoring:latest
docker login --username $DOCKER_USERNAME --password $DOCKER_PASSWORD
docker push lukaszwelnicki/gas-turbine-monitoring:latest
rm api.jar