FROM amazoncorretto:17

ENV APP_HOME=/choisinsa
ENV APP_NAME=module-app-choisinsa

EXPOSE 8080
ENV PORT 8080
ENV PROFILE "client"

WORKDIR ${APP_HOME}

COPY . .
RUN ./gradlew clean build

COPY ./module-app-choisinsa/build/libs/*.jar ./module-app-choisinsa/build/libs/module-app-choisinsa.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=client", "-jar", "module-app-choisinsa/build/libs/module-app-choisinsa.jar"]