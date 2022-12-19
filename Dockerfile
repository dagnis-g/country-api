FROM openjdk:17-oracle as buildstage
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package
COPY target/*.jar app.jar

FROM openjdk:17-oracle
EXPOSE 8080
COPY --from=buildstage /app/app.jar .
ENTRYPOINT ["java", "-jar", "app.jar"]