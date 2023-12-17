# 루트에서 명령어 실행 / docker build -t itchoi0429/choi-admin-app -f ./module-api-admin/Dockerfile ./
FROM gradle:7.5 as gradle-builder

WORKDIR /build

COPY ./build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle clean build --parallel

FROM adoptopenjdk:16

ENV APP_HOME=/usr/choisinsa/
ENV APP_NAME=module-api-admin-app

EXPOSE 8081
ENV PORT 8081
ENV PROFILE "admin"

#COPY ./gradlew .
#RUN chmod +x ./gradlew
#RUN ./gradlew clean build
COPY --from=gradle-builder /build/build/libs/*.jar /module-api-admin.jar

WORKDIR ${APP_HOME}
#ARG JAR_FILE=./build/libs/*.jar
#
#COPY ${JAR_FILE} /module-api-admin.jar
#ENTRYPOINT ["java","-jar","-Dspring.profiles.active=admin","module-api-admin.jar"]

ENTRYPOINT ["java", "-Dspring.profiles.active=admin", "-jar", "/module-api-admin.jar"]
