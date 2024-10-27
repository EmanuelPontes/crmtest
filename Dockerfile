# Use an official OpenJDK 17 runtime as a base image
FROM maven:3.8.3-openjdk-17

# Set the working directory inside the container
WORKDIR /home/app
COPY . .

# Expose the port on which the Spring Boot app runs (default is 8080)
EXPOSE 8080

RUN mvn install
# Set the command to run the jar file
ENTRYPOINT ["mvn","spring-boot:run"]
