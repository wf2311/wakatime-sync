FROM openjdk:8-jdk-alpine as builder
MAINTAINER wf2311 "wf2311@163.com"
WORKDIR application
COPY target/*.jar application.jar
#COPY bin/docker-startup.sh bin/startup.sh
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:8-jdk-alpine
MAINTAINER wf2311 "wf2311@163.com"
WORKDIR application
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/wf2311-dependencies/ ./
COPY --from=builder /application/application/ ./
ADD bin/docker-startup.sh bin/startup.sh

ENV JVM_OPTS '-Xmx256m -Xms64m -Xss256k'
ENV JVM_AGENT ''
ENV SERVER_PORT 3040
ENV WAKATIME_APP_KEY ''
ENV WAKATIME_PROXY_URL 'false'
ENV WAKATIME_FTQQ_KEY ''
ENV WAKATIME_DINGDING_KEY ''
ENV START_DAY '2016-01-01'
ENV MYSQL_URL 'jdbc:mysql://127.0.0.1:3306/wakatime?characterEncoding=utf8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=PRC'
ENV MYSQL_USERNAME 'wakatime'
ENV MYSQL_PASSWORD '123456'
EXPOSE ${SERVER_PORT}

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone \
    && mkdir logs \
    && cd logs \
    && touch start.out \
    && ln -sf /dev/stdout start.out \
    && ln -sf /dev/stderr start.out

RUN chmod +x bin/startup.sh
ENTRYPOINT [ "sh", "-c", "sh bin/startup.sh"]
