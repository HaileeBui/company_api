FROM amazoncorretto:17-alpine-jdk

#Creat a directory for app
WORKDIR /app

#Copy to the app directory
COPY . .

#Build the app
RUN ./gradlew clean build -x test

#Expose port
EXPOSE 8080

#Run the app
CMD ["java", "-jar", "./build/libs/company-api-0.0.1-SNAPSHOT.jar"]

