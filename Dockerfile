FROM openjdk:11
LABEL authors="Priyanshu"
EXPOSE 8080
ADD target/test-0.0.1-SNAPSHOT.jar test-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/test-0.0.1-SNAPSHOT.jar"]