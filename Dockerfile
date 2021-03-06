FROM openjdk:11
ADD target/peopleflow-0.0.1-SNAPSHOT.jar peopleflow-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "peopleflow-0.0.1-SNAPSHOT.jar"]