#!/bin/bash

#项目名称
APP_NAME='wakatime-sync'
#项目启动端口号
SERVER_PORT='3040'
#项目启动需要附加的jvm参数
JVM_OPTS='-Xmx256m -Xms256m'
#项目启动需要附加的指定的javaagent
JVM_AGENT=''
#项目打包上传的docker仓库地址
REGISTRY_HOST=''
#项目打包的镜分组
REGISTRY_GROUP='wf2311'
#项目打包的镜像名称
REGISTRY_IMAGE='wakatime-sync:1.1'
#远程docker仓库登录用户名
#REGISTRY_USER='admin'
#远程docker仓库登录密码
#REGISTRY_PASSWORD='Password@1'

cd ../
echo "stage mvn package"
mvn -DskipTests package -Pleke

echo "build and push docker image"

#docker login -u $REGISTRY_USERNAME -p $REGISTRY_PASSWORD $REGISTRY_HOST
docker build --build-arg APP_NAME=$APP_NAME -t $REGISTRY_GROUP/$REGISTRY_IMAGE .
docker push $REGISTRY_GROUP/$REGISTRY_IMAGE
