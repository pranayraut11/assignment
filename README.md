# Sample Microservice Project
This project in based on microservices architecture which includes User and User Profile related functionality.

## Getting Started

## Prerequisites

* Java 8
* Maven 3
* Git
* Kafka 2.12

## Features

* Reactive
* Event-Driven


## Clone
To get started you can simply clone this repository using git:
```
git clone https://github.com/pranayraut11/assignment.git
```
## Installing
```
Install Kafka : https://kafka.apache.org/quickstart
Create topic : "demo-topic"
```
Goto discovery-server project and run command : 
```
mvn clean install 
mvn spring-boot:run 
```
Goto authorization-service project and run command : 
```
mvn clean install 
mvn spring-boot:run
```
Goto profile-service project and run command :
```
mvn clean install 
mvn spring-boot:run
```

## Servers
authorizatio-service uses Tomcat <br>
profile-service uses Netty

## Database 
It uses a H2 in memory database (for now), can be changed easily in the application.properties for any other database.

To check "user" table in H2 database use
```
http://localhost:9090/h2-console/
username : sa
password : password
```
```
Note : we can not access h2-console for profile-service because we are using Netty server.
```
### Default data
Add one record to user table <br> 
```    
insert into USER values (1,'$2a$10$FhAL6iexaBBKjG03nGcu9eZawQ1hyfq9SGdXcIEM2J82FqQ4TBU0C','demo');
```    
    
## Running the test
Use following endpoints

1 : Login API method POST 
```
http://localhost:9090/login?username=demo&password=123
```
2 : Save profile API method POST 
```
http://localhost:9090/profile
```
Request Json 
```
{  
    "userId":"1",
    "address":"38",
    "mobile" : "987987987987"
}
```
3 : Update profile API method PUT
```
http://localhost:9090/profile
```
Request Json 
```
{ 
      "userId":"1",
      "address":"104",
      "mobile" : "987987987"
}
```
4 : Delete profile API method DELETE 
```
http://localhost:9090/profile?userId=1
```
5 : Logout 
```
http://localhost:9090/logout    
```    

## Built With
Maven
