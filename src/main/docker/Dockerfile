FROM frolvlad/alpine-oraclejdk8:slim
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME /tmp
ADD wakatime-sync.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xmx256m -Xms64m -Xss256k"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar >> server.log 2>&1" ]