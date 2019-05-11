# CHARGE STATION API

This project aims to demonstrate a prototype of electric car charge stations' back-end service. 
This REST API has three main jobs, basically; creating a new charging session, finishing a charging 
session and getting all created sessions so far.
    

### PREREQUISITES

1) Java 1.8 JDK
2) Maven 

* They should be set as path variables.
* It can be easily checked by using commands `mvn --version` and `java -version` on command prompt. 
   


### HOW TO RUN

If everything is ok so far, open a command promt at project root directory;
1) The project can be run by using `mvn spring-boot:run` or,
2) Creating a jar file with `mvn package` and executing that by `java -jar ./target/charge-station-api-1.0-SNAPSHOT.jar`