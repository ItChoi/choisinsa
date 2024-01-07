# docker image build -t itchoi0429/choisinsa-client-app ./
# docker container run --name client-api -d -p 9090:8080 --network choisinsa-app-network itchoi0429/choisinsa-client-app
FROM gradle:7.5 as gradle-builder
WORKDIR /build

COPY ./build.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY ./ /build
RUN gradle build --parallel

FROM adoptopenjdk:16

ENV APP_HOME=/choisinsa
ENV APP_NAME=choisinsa-client-app

EXPOSE 8080
ENV PORT 8080
ENV PROFILE "client"

#RUN sudo chmod +x ../gradlew
#RUN ../gradlew clean build

WORKDIR ${APP_HOME}

COPY --from=gradle-builder /build/build/libs/*.jar ./module-api-client.jar
RUN chmod +x ./module-api-client.jar


#ARG JAR_FILE=./build/libs/*.jar
#
#COPY ${JAR_FILE} /module-api-client-app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=client", "-jar", "module-api-client.jar"]
