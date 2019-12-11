#!/bin/bash
#############################
# 请仔细阅读以下说明和注意事项 #
#############################

# 说明：
# 由于项目依赖的外部服务越来越多，导致搭建开发环境复杂度越来越高
# 编写此自动化脚本主要是为了方便开发环境的移植，也方便其他开发者运行这个项目
# 注意事项：
# 请在CentOS7服务器上登录root用户运行此脚本
# 执行过程中需要获取网络资源，请提前配置网络及固定ipv4地址
# 为避免执行过程中由于网络不通等其他原因导致执行失败，请在执行此脚本前创建虚拟机快照
# 脚本仍不完善，没有对失败的情况做补偿，经过测试，失败率较低，若执行失败，请手动恢复快照，重新执行
# 此脚本只需要成功执行一次，虚拟机重启之后只需重新启动docker和容器（默认自启动）

# 检查当前是否root用户
if [[ `whoami` != "root" ]];then
  echo "请使用root用户运行此脚本"
  exit 1
fi

# 确认提示
echo "自动初始化脚本即将开始执行，是否确认继续"
read -p "请输入yes或no: " -r confirm
if [[ $confirm != "yes" ]];then
  echo "退出安装"
  exit 1
fi

# 安装wget
yum -y install wget

# 修改yum源
\mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bak
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum clean all
yum makecache fast

# 安装zip和unzip
yum -y install zip unzip

# 将ik分词器解压到/etc/elasticsearch/plugins/ik
mkdir -p /etc/elasticsearch/plugins/ik
unzip -o -d /etc/elasticsearch/plugins/ik /root/elasticsearch-analysis-ik-6.4.3.zip

# 读取docker镜像加速地址
# https://ey9rkwik.mirror.aliyuncs.com
regex_url="^(ht|f)tp(s?)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-\.\?\,\'\/\\\+&amp;%\$#_]*)?$"
while [[ `echo $docker_mirror | grep -E $regex_url` == "" ]]
do
  echo "请输入docker镜像加速地址:"
  read -p "docker镜像加速地址:" -r docker_mirror
done

echo "开始安装..."
sleep 3

# 安装docker
# 安装必要的一些系统工具
yum install -y yum-utils device-mapper-persistent-data lvm2
# 添加软件源信息
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# 安装docker-ce
yum -y install docker-ce
# 开启docker服务
systemctl start docker

# 配置docker镜像加速
mkdir -p /etc/docker
tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["docker_mirror"]
}
EOF
# 替换为真实镜像地址
sed -i "s#docker_mirror#$docker_mirror#g" /etc/docker/daemon.json

# 暴露2375端口
sed -i \
's#ExecStart=/usr/bin/dockerd -H fd://#ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375#g' \
/usr/lib/systemd/system/docker.service

# 重启docker服务
systemctl daemon-reload
systemctl restart docker

# docker开机自启动
systemctl enable docker

sleep 5

# mysql配置文件
mkdir -p /etc/mysql
tee /etc/mysql/my.cnf <<-'EOF'
[mysqld]
port=3306
max_connections=200
character-set-server=utf8mb4
default-storage-engine=INNODB
explicit_defaults_for_timestamp=true
default-time-zone='+08:00'
lower_case_table_names=1
group_concat_max_len=10240
skip-host-cache
skip-name-resolve
[mysqldump]
quick
quote-names
max_allowed_packet=16M
EOF

# nginx配置文件
mkdir -p /etc/nginx
tee /etc/nginx/nginx.conf <<-'EOF'
user root;

worker_processes  1;

error_log  /var/log/nginx/error.log  info;

worker_rlimit_nofile 1024;

events {
    worker_connections  1024;
}

http {
    proxy_http_version 1.1;
    proxy_connect_timeout 600;
    proxy_read_timeout 600;
    proxy_send_timeout 600;
    client_max_body_size 50m;
    include       mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    keepalive_timeout  65;

    sendfile on;

    gzip on;
    gzip_buffers 16 8k;
    gzip_comp_level 2;
    gzip_http_version 1.1;
    gzip_min_length 256;
    gzip_proxied any;
    gzip_vary on;

    gzip_types
        text/xml application/xml application/atom+xml application/rss+xml application/xhtml+xml image/svg+xml
        text/javascript application/javascript application/x-javascript
        text/x-json application/json application/x-web-app-manifest+json
        text/css text/plain text/x-component
        font/opentype font/ttf application/x-font-ttf application/vnd.ms-fontobject
        image/x-icon image/jpeg image/gif image/png;

    server {
        listen 80;
        server_name portalfile;
        location ^~/portalfile/ {
            add_header 'Access-Control-Allow-Origin' '*';
            add_header 'Access-Control-Allow-Credentials' 'true';
            alias /home/portalfile/;
        }
    }

}
EOF

# 容器脚本
# mysql
tee mysql.sh <<-'EOF'
#!/bin/bash
docker pull mysql:5.7.26
docker stop mysql
docker rm mysql
docker run -d --name mysql \
--restart=always \
-e MYSQL_ROOT_PASSWORD=root \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-v /etc/mysql:/etc/mysql/conf.d \
-p 3306:3306 \
mysql:5.7.26
EOF

# nginx
tee nginx.sh <<-'EOF'
#!/bin/bash
docker pull nginx:1.17
docker stop nginx
docker rm nginx
docker run -d --name nginx \
--restart=always \
-v /etc/nginx/nginx.conf:/etc/nginx/nginx.conf \
-v /var/log/nginx:/var/log/nginx \
-v /home/portalfile:/home/portalfile \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-p 80:80 \
nginx:1.17
EOF

# redis
tee redis.sh <<-'EOF'
#!/bin/bash
docker pull redis:5
docker stop redis
docker rm redis
docker run -d --name redis \
--restart=always \
-v /opt/redis:/data \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-p 6379:6379 \
redis:5 \
redis-server --appendonly yes \
--requirepass portal
EOF

# rabbitmq
tee rabbitmq.sh <<-'EOF'
#!/bin/bash
docker pull rabbitmq:3.7-management
docker stop rabbitmq
docker rm rabbitmq
docker run -d --name rabbitmq \
--restart=always \
--hostname rabbitmq \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-p 5672:5672 \
-p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=portal \
-e RABBITMQ_DEFAULT_PASS=portal \
rabbitmq:3.7-management
EOF

# elasticsearch
tee elasticsearch.sh <<-'EOF'
#!/bin/bash
docker pull elasticsearch:6.4.3
docker stop elasticsearch
docker rm elasticsearch
docker run -d --name elasticsearch \
--restart=always \
-e ES_JAVA_OPTS="-Xms512m -Xmx512m" \
-e "discovery.type=single-node" \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-v /etc/elasticsearch/plugins/ik:/usr/share/elasticsearch/plugins/ik \
-p 9200:9200 \
-p 9300:9300 \
elasticsearch:6.4.3
EOF

# 设置时区
rm -rf /etc/timezone
echo "Asia/Shanghai" > /etc/timezone

# 创建portalfile用户
if [[ `cat /etc/passwd | cut -f1 -d':' | grep -w "portalfile" -c` -le 0 ]];then
  adduser portalfile
  echo "portal" | passwd --stdin portalfile
fi

# 创建并启动容器
sh mysql.sh
sh nginx.sh
sh redis.sh
sh rabbitmq.sh
sh elasticsearch.sh

# 关闭防火墙
systemctl stop firewalld
systemctl disable firewalld

# 创建并初始化数据库
docker cp init.sql mysql:/root/
docker exec -i mysql mysql -uroot -proot -e "source /root/init.sql;"

echo "安装完成"
