FROM maven:3.6.1-jdk-8-alpine AS package

RUN apk add --update \
    curl \
    jq

RUN mkdir -p /app
WORKDIR /app

COPY pom.xml                          .
COPY healthcheck.sh                   .
RUN mvn -e -B dependency:resolve

COPY src                              ./src
RUN mvn verify --fail-never -DskipTests

WORKDIR /app/

ENTRYPOINT ["/bin/sh"]
CMD ["healthcheck.sh"]

FROM openjdk:8-jre-alpine AS testrun

RUN mkdir -p /jar
WORKDIR /jar/

COPY --from=package /app/target/dockerized.jar         .
COPY --from=package /app/target/dockerized-tests.jar   .
COPY --from=package /app/target/libs                        ./libs
COPY testng.xml                                             .
COPY src/main/resources/allure.properties                   ./src/main/resources/allure.properties
COPY src/main/resources/application.properties              ./src/main/resources/application.properties
COPY run.sh                                                 .

WORKDIR /jar/

ENV HUB_HOST=hub
ENV MODULE=testng.xml

ENTRYPOINT ["/bin/sh"]
CMD ["run.sh"]