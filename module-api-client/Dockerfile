# 적용 중 .... 테스트 중....
# docker image build -t itchoi0429/choisinsa-client-app ./
# docker container run --name client-api -d -p 9090:8080 --network choisinsa-app-network itchoi0429/choisinsa-client-app
FROM adoptopenjdk:16

ENV APP_HOME=/usr/app/
ENV APP_NAME=choisinsa-client-app

EXPOSE 8080
ENV PORT 8080
ENV PROFILE "client"

RUN chmod u+x ../../gradlew
RUN ../../gradlew clean build

WORKDIR ${APP_HOME}

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} /choisinsa-client-app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=client","choisinsa-client-app.jar"]
