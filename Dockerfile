FROM  openjdk:17-jdk-slim


##add comment


ADD target/FabLab-Back-0.0.1-SNAPSHOT.jar FabLab-Back-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","FabLab-Back-0.0.1-SNAPSHOT.jar"]
