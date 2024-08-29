FROM amazoncorretto:17

# 필요한 패키지 설치
RUN yum update -y && \
    yum install -y \
    wget \
    unzip \
    google-chrome-stable \
    && yum clean all

VOLUME /tmp

ARG JAR_FILE=build/libs/Shop-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]