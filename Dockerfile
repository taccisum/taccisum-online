FROM openjdk:8

WORKDIR /workspace/

ADD ./target/app.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
