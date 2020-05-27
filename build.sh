project_name=$1
port=$2
cd /root/$project_name
echo "编译项目..."
mvn -DskipTests clean package docker:build
echo "stop $project_name..."
docker ps -a | grep $1 | awk '{print $1}' | xargs -I % sh -c "docker stop % && docker rm %"

echo "start $project_name port=$port ..."
log_dir="/usr/local/myWeb/logs/$1"
log_file="$log_dir/server.log"
if [ ! -d "$log_dir" ]; then
  mkdir $log_dir
fi
docker run -v $log_dir:/home/app -w /home/app -p $port:$port --name $project_name -d $project_name:lastet
docker rmi -f `docker images | grep '<none>' | awk '{print $3}'`

# docker run -v /usr/local/myWeb/logs/wakatime-sync:/home/app -w /home/app -p 3040:3040 --name wakatime-sync -d wakatime-sync:lastet
