####
# This Dockerfile is used in order to build a distroless container that runs the Quarkus application in native (no JVM) mode
#
# Before building the container image run:
#
# ./mvnw package -Pnative
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile.native-micro -t quarkus/hibernate-orm-panache .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/hibernate-orm-panache
#
###
FROM quay.io/quarkus/quarkus-micro-image:1.0
COPY target/*-runner /application

EXPOSE 3040
USER 1001

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

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
