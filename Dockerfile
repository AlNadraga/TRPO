FROM openjdk:alpine

RUN apk add --no-cache maven
ADD src /TRPO/src
ADD pom.xml /TRPO/pom.xml
WORKDIR /TRPO
RUN mvn clean install -e

CMD ["java","-jar","/usr/src/app/target/json-validator-0.1-jar-with-dependencies.jar"]
EXPOSE 80
