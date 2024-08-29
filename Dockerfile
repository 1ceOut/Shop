FROM openjdk:17.0.1-jdk-slim

RUN apt update

RUN apt install wget -y

RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb

RUN dpkg -i ./google-chrome-stable_current_amd64.deb

VOLUME /tmp

ARG JAR_FILE=build/libs/Shop-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]