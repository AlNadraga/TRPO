FROM openjdk:alpine
CMD ["java","-jar","/usr/src/app/target/json-validator-0.1-jar-with-dependencies.jar"]
EXPOSE 80
