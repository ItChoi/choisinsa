# 루트에서 명령어 실행 / docker build -t itchoi0429/choi-admin-app -f ./module-api-admin/Dockerfile ./
FROM gradle:7.5 as gradle-builder
WORKDIR /build

COPY ./build.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY ./ /build/
RUN gradle build --parallel

FROM adoptopenjdk:16

ENV APP_HOME=/choisinsa
ENV APP_NAME=choisinsa-admin-app

EXPOSE 8081
ENV PORT 8081
ENV PROFILE "admin"

WORKDIR ${APP_HOME}

COPY --from=gradle-builder /build/libs/*.jar ./module-api-admin.jar
RUN chmod +x ./module-api-admin.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=admin", "-jar", "module-api-admin.jar"]
