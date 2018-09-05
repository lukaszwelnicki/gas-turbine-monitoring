[![CircleCI](https://circleci.com/gh/lukaszwelnicki/gas-turbine-monitoring.svg?style=svg)](https://circleci.com/gh/lukaszwelnicki/gas-turbine-monitoring)

[![codecov](https://codecov.io/gh/lukaszwelnicki/gas-turbine-monitoring/branch/master/graph/badge.svg)](https://codecov.io/gh/lukaszwelnicki/gas-turbine-monitoring)

# gas-turbine-monitoring

This application is responsible for storing and streaming heavy-duty gas turbine sensor's data. 
Measurements are generated randomly and stored into capped collection in MongoDB.
Database's collections provide tailable cursors to allow continuous data streaming via Spring Data JPA reactive repositories.
MongoDB is established using docker-compose.yml. Sensor's data can be streamed to the client using Server-Sent Events.

# Installation

Run "docker-compose up" inside docker catalog. You don't even need to clone this repository. Docker-compose is everything that is required.

# Endpoints

User can start the asynchronous database filling process emulating sensor's data gathering
by triggering the following endpoint:

- localhost:8081/fill

Data is sampled within time intervals defined in application.yml. To stop this process, use:

- localhost:8081/stopfill

This will dispose the streaming process.

To obtain sensors data, use the following endpoints:

- localhost:8081/measurements/{collection_name}, 

where {collection_name} should be replaced with one of the following:

- aft_bmt
- compressor_efficiency
- forward_bmt
- generator_vibrations
- turbine_efficiency
- turbine_vibrations

If the database fill process is running, data should be streamed from MongoDB thanks to tailable cursors 
and Server-Sent Events

# Tests execution
Testcontainers framework was used to provide mongoDB Docker container for the purpose of integration testing.

# Continuous Integration
CI has been established using CircleCI. Creating an Docker image out of the Springboot app and pushing it to Docker-Hub has been integrated into CI cycle. 

# Technologies used

- Spring Boot 2.0
- Spring Webflux
- Project Reactor
- Vavr.io
- SpockFramework
- Testcontainers
- MongoDB
- Java 1.8
