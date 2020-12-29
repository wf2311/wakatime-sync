FROM openjdk:8-jdk-alpine
WORKDIR /home/app
ENV JAVA_OPTS '-Xmx256m -Xms64m -Xss256k'
ENV JVM_AGENT ''
ENV SERVER_PORT 3040
ENV WAKATIME_APP_KEY 'wakatime.secret-api-key'
ENV WAKATIME_PROXY_URL 'false'
ENV WAKATIME_FTQQ_KEY 'false'
ENV WAKATIME_DINGDING_KEY 'false'
ENV START_DAY '2020-01-01'
ENV MYSQL_URL 'jdbc:mysql//localhost:3306/wakatime'
ENV MYSQL_USERNAME 'wakatime'
ENV MYSQL_PASSWORD '123456'
EXPOSE ${SERVER_PORT}
COPY target/*.jar /home/app/app.jar

RUN touch app.jar \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone \
    && mkdir logs
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS $JVM_AGENT -Dspring.datasource.url=$MYSQL_URL  -Dspring.datasource.username=$MYSQL_USERNAME -Dspring.datasource.password=$MYSQL_PASSWORD -Dwakatime.secret-api-key=$WAKATIME_APP_KEY -Dwakatime.proxy-url=$WAKATIME_PROXY_URL -Dwakatime.start-day=$START_DAY  -Dwakatime.ftqq-key=$WAKATIME_FTQQ_KEY -Dwakatime.dingding-key=WAKATIME_DINGDING_KEY -D -Dserver.port=$SERVER_PORT -Djava.security.egd=file:/dev/./urandom -jar /home/app/app.jar >> /home/app/logs/server.log 2>&1" ]
