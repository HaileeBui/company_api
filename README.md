# Introduction
This is a Company REST API where user can use to save/get/delete specific company or get a whole list of saved companies

# How to run
1. Clone the app from github
2. Open with any code editor for example Intellij, build and run the app

# Technical
Spring Boot - Kotlin - Gradle

The app uses H2 Database because it is lightweight and faster to return data. I think it is suitable to use this to build a small demo. In order to connect, I created a schemas and configuration file.
When adding new company, the Id will be automatically assigned.

Since Spring Boot has many layers, we need to pass the object through those layers. For that reason, I created Entity and DTO classes.
Mapper is used to convert Entity to Domain and the other way around.

Controller class will call the service, service class will handle business logic and validation. 

To fetch data from external API, I use RestTemplate. I also create ErrorHandler class, so it would be easier to determine the cause of issue; for example, BAD_REQUEST or NOT_FOUND 

# Testing
There is testing file in the test folder

Note: There is one test I could not get it work as expected. The test is to return phone number and address when input business ID. But the call works well with Postman

# Postman
Open Postman and try to call method according to openapi.yml. 

For example, GET http://localhost:8080/company

# Extra question

When talking about serverless, i think of AWS Lambda or Fargate. And there can be other supported services such as Amazon API Gateway for front end and RDS for relational database

I think we need to pay attention to CORS and security (only specific user can access the server). And since Lambda is stateless, we need to use RDS for database