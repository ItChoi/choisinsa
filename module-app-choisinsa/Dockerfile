FROM amazoncorretto:17

ENV APP_HOME=/choisinsa
ENV APP_NAME=module-app-choisinsa

EXPOSE 8080
ENV PORT 8080
ENV PROFILE "client"

WORKDIR ${APP_HOME}

COPY . .

COPY ./module-app-choisinsa/src/main/resources/application-database.yml .module-app-choisinsa/src/main/resources/application-database.yml
COPY ./module-app-choisinsa/src/main/resources/application-oauth.yml .module-app-choisinsa/src/main/resources/application-oauth.yml
COPY ./module-redis/src/main/resources/application-redis.yml .module-app-choisinsa/src/main/resources/application-redis.yml

RUN chmod +x gradlew
RUN ./gradlew clean build

RUN ls -la module-app-admin/
RUN ls -al module-app-admin/build/
RUN ls -al module-app-admin/build/libs


ENTRYPOINT ["java", "-Dspring.profiles.active=client", "-jar", "module-app-choisinsa/build/libs/module-app-choisinsa-0.0.1-SNAPSHOT.jar"]