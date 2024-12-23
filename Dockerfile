FROM openjdk:17-jdk-slim

# Expose the port your application will run on
EXPOSE 8006

# Add the JAR file to the container (from the target folder in the host machine to the container)
ADD target/UserMS-0.0.1-SNAPSHOT.jar UserMS-0.0.1-SNAPSHOT.jar

# Set the entrypoint to run the Spring Boot JAR file
ENTRYPOINT ["java", "-jar", "UserMS-0.0.1-SNAPSHOT.jar"]
