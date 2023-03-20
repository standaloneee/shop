# Build
FROM gradle:7.6-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --no-daemon

# Run
FROM openjdk:17
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/shop.jar
ENTRYPOINT ["java", "-jar", "/app/picker.jar"]