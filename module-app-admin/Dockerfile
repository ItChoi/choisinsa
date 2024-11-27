FROM amazoncorretto:17

ENV APP_HOME=/choisinsa
ENV APP_NAME=module-app-admin

EXPOSE 8081
ENV PORT 8081
ENV PROFILE "admin"

WORKDIR ${APP_HOME}

COPY . .
RUN ./gradlew clean build

COPY ./module-app-admin/build/libs/*.jar ./module-app-admin/build/libs/module-app-admin.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=client", "-jar", "module-app-admin/build/libs/module-app-admin.jar"]