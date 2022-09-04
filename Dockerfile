FROM gradle:7.3.1-jdk17 AS TEMP_BUILD_IMAGE
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar

FROM openjdk:17

ENV ARTIFACT_NAME="make-mark-0.0.1.jar"
ENV APP_HOME=/home/gradle/src

EXPOSE ${APP_PORT}

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE /home/gradle/src/build/libs/$ARTIFACT_NAME .

ENTRYPOINT ["java", "-jar", "make-mark-0.0.1.jar"]