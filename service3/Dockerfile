FROM adoptopenjdk/openjdk11:alpine-slim as BUILDER

WORKDIR /usr/src/app2/
COPY mvnw pom.xml /usr/src/app2/
COPY src /usr/src/app2/src
COPY .mvn /usr/src/app2/.mvn
RUN dos2unix mvnw
RUN ./mvnw -DskipTests clean package


FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=BUILDER /usr/src/app2/target/*.jar /server.jar
CMD ["java", "-jar", "/server.jar"]