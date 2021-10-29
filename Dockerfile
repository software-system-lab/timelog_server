FROM openjdk:8
ADD ./target/timelog_server-2.1.0.jar /app/
WORKDIR /app

# Run spring-boot server
CMD ["java", "-jar", "timelog_server-2.1.0.jar"]
