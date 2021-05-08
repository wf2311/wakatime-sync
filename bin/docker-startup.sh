#!/bin/bash
BASE_DIR=/application

JAVA_OPT="$JAVA_OPT $JVM_OPTS"
JAVA_OPT="$JAVA_OPT $JVM_AGENT"
JAVA_OPT="$JAVA_OPT -Dspring.datasource.url=$MYSQL_URL"
JAVA_OPT="$JAVA_OPT -Dspring.datasource.username=$MYSQL_USERNAME"
JAVA_OPT="$JAVA_OPT -Dspring.datasource.password=$MYSQL_PASSWORD"
JAVA_OPT="$JAVA_OPT -Dwakatime.secret-api-key=$WAKATIME_APP_KEY"
JAVA_OPT="$JAVA_OPT -Dwakatime.proxy-url=$WAKATIME_PROXY_URL"
JAVA_OPT="$JAVA_OPT -Dwakatime.start-day=$START_DAY"
JAVA_OPT="$JAVA_OPT -Dwakatime.ftqq-key=$WAKATIME_FTQQ_KEY"
JAVA_OPT="$JAVA_OPT -Dwakatime.dingding-key=$WAKATIME_DINGDING_KEY"
JAVA_OPT="$JAVA_OPT -Dserver.port=$SERVER_PORT"
JAVA_OPT="$JAVA_OPT org.springframework.boot.loader.JarLauncher"

echo "application is starting,you can check the ${BASE_DIR}/logs/start.out"
echo "java $JAVA_OPT" >$BASE_DIR/logs/start.out 2>&1 &
nohup java $JAVA_OPT >$BASE_DIR/logs/start.out 2>&1 </dev/null &
tail $BASE_DIR/logs/start.out -f