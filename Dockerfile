FROM amazoncorretto:17

# 필요한 패키지 설치
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm

RUN yum install google-chrome-stable_current_x86_64.rpm

VOLUME /tmp

ARG JAR_FILE=build/libs/Shop-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]