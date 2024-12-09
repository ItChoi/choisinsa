FROM amazoncorretto:17

ENV APP_HOME=/choisinsa
ENV APP_NAME=module-app-admin

EXPOSE 8081
ENV PORT 8081
ENV PROFILE "admin"

WORKDIR ${APP_HOME}

COPY . .

COPY ./module-app-admin/src/main/resources/application-database.yml .module-app-admin/src/main/resources/application-database.yml
COPY ./module-redis/src/main/resources/application-redis.yml .module-app-admin/src/main/resources/application-redis.yml

RUN chmod +x gradlew
RUN ./gradlew clean build

RUN ls -la module-app-admin/
RUN ls -al module-app-admin/build/
RUN ls -al module-app-admin/build/libs

ENTRYPOINT ["java", "-Dspring.profiles.active=admin", "-jar", "module-app-admin/build/libs/module-app-admin-0.0.1-SNAPSHOT.jar"]