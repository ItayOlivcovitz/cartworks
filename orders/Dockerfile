# Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

# Information around who maintains the image
LABEL "org.opencontainers.image.authors"="itayolivcovitz@example.com"

# Add the application's jar to the image
COPY target/orders-0.0.1-SNAPSHOT.jar orders-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "orders-0.0.1-SNAPSHOT.jar"]
