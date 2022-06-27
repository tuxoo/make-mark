FROM openjdk:11

WORKDIR /app
ADD /build/libs/make-mark-0.0.1-SNAPSHOT.jar mmark.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "mmark.jar"]