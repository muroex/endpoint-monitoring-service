FROM java:11
EXPOSE 8080
ADD target/endpoint-monitoring-service-0.0.1-SNAPSHOT.jar endpoint-monitoring-service.jar
ENTRYPOINT ["java","-jar","endpoint-monitoring-service.jar"]