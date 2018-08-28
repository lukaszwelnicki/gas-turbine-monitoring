gradlew clean build
docker build -t lws-api .
docker tag lws-api lukaszwelnicki/lws-api:0.0.1-SNAPSHOT
docker push lukaszwelnicki/lws-api:0.0.1-SNAPSHOT