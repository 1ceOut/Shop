FROM finalregister.kr.ncr.ntruss.com/selenium

VOLUME /tmp

ARG JAR_FILE=build/libs/Shop-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]