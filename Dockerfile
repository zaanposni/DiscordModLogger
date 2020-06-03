FROM maven:3.6.0-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn -f /app/pom.xml clean package

# copy config.json if it exists

FROM openjdk:11-jre-slim
COPY --from=build /app/target/discordModLogger.jar /bin
COPY default-config.json config.json* /bin/
WORKDIR /bin  
ENTRYPOINT ["java","-jar","discordModLogger.jar"]
