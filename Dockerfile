FROM openjdk:17.0.1-jdk-slim

# 패키지 목록 업데이트 및 필요한 패키지 설치
RUN apt-get -y update && \
    apt-get -y install wget unzip curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Google Chrome 설치
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get -y install ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb

# ChromeDriver 설치
RUN CHROME_VERSION=$(google-chrome --version | grep -oP '\d+\.\d+\.\d+\.\d+') && \
    CHROMEDRIVER_VERSION=$(curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_VERSION) && \
    wget -q -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip && \
    unzip /tmp/chromedriver.zip -d /usr/bin && \
    chmod +x /usr/bin/chromedriver && \
    rm /tmp/chromedriver.zip


# 임시 파일을 위한 볼륨 설정
VOLUME /tmp

# 복사할 JAR 파일 지정
ARG JAR_FILE=build/libs/Shop-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 애플리케이션의 엔트리 포인트 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
