# Use the official OpenJDK image as a base
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy compiled JAR file and any other necessary files
COPY target/mipro-traingraph-1.jar /app/mipro-traingraph-1.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "mipro-traingraph-1.jar"]