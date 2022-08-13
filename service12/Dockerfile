FROM adoptopenjdk/openjdk11:alpine-slim as BUILDER

WORKDIR /usr/src/app/
COPY mvnw pom.xml /usr/src/app/
COPY src /usr/src/app/src
COPY .mvn /usr/src/app/.mvn
RUN dos2unix mvnw
RUN ./mvnw -DskipTests clean package


FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=BUILDER /usr/src/app/target/*.jar /server.jar
CMD ["java", "-jar", "/server.jar"]