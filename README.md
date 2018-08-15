# msc-reactive

This application is responsible for storing and streaming heavy-duty gas turbine sensor's data. 
Measurements are generated randomly and stored into capped collection in MongoDB.
Database's collections provide tailable cursors to allow continuous data streaming via Spring Data JPA reactive repositories.
MongoDB is established using docker-compose.yml.

Technologies used:
- Spring Boot 2.0
- Spring Webflux
- Vavr.io
- SpockFramework
- MongoDB
- Java 1.8
