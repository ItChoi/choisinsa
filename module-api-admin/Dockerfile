FROM adoptopenjdk:16

ENV APP_HOME=/usr/app/
ENV APP_NAME=choisinsa-admin-app

EXPOSE 8081
ENV PORT 8081
ENV PROFILE "admin"

RUN chmod u+x ../../gradlew
RUN ../../gradlew clean build

WORKDIR ${APP_HOME}

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} /choisinsa-admin-app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=admin","choisinsa-admin-app.jar"]
