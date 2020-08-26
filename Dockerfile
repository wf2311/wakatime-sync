FROM maven:3-jdk-8 as maven3
WORKDIR /var/www
COPY src src
COPY pom.xml pom.xml
RUN mvn -DskipTests clean package

FROM carsharing/alpine-oraclejdk8-bash
WORKDIR /var/www
EXPOSE 3040
ENV JAVA_OPTS="-Xmx256m -Xms64m -Xss256k"
COPY --from=maven3 /var/www/target/wakatime-sync.jar wakatime-sync.jar
RUN touch wakatime-sync.jar \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone \
    && mkdir log
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /var/www/wakatime-sync.jar >> /var/www/log/server.log 2>&1" ]