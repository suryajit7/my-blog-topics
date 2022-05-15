FROM maven:3.6.0-jdk-11-slim AS package

RUN apt-get update && apt-get install -y \
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

FROM fabric8/java-alpine-openjdk11-jre AS testrun

RUN mkdir -p /jar
WORKDIR /jar/

COPY --from=package /app/target/dockerized.jar              .
COPY --from=package /app/target/dockerized-tests.jar        .
COPY --from=package /app/target/libs                        ./libs
COPY testng.xml                                             .
COPY src/main/resources/allure.properties                   ./src/main/resources/allure.properties
COPY src/main/resources/application.properties              ./src/main/resources/application.properties
COPY src/test/resources/test-data                           ./src/test/resources/test-data/
COPY run.sh                                                 .

WORKDIR /jar/

ENV HUB_HOST=hub
ENV MODULE=testng.xml

ENTRYPOINT ["/bin/sh"]
CMD ["run.sh"]