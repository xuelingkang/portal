#!/bin/bash
# 使用docker容器运行一些开发环境依赖的服务

# mysql
docker run -d --name mysql \
--restart=always \
-e MYSQL_ROOT_PASSWORD=root \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-v $(pwd)/my.cnf:/etc/mysql/my.cnf \
-p 3306:3306 \
mysql:8.0.21

# 初始化数据
docker cp quartz.sql mysql:/quartz.sql
docker cp portal_content.sql mysql:/portal_content.sql
result=1
while [ $result -ne 0 ]
do
  sleep 2
  docker exec mysql mysql -uroot -proot -e "SET CHARACTER SET utf8mb4;DROP DATABASE IF EXISTS portal_content;CREATE DATABASE portal_content DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;USE portal_content;SOURCE /portal_content.sql;SOURCE /quartz.sql;"
  result=$?
done

# redis
docker run -d --name redis \
--restart=always \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-p 6379:6379 \
redis:6.0.6 \
redis-server --appendonly yes \
--requirepass portal

# rabbitmq
docker run -d --name rabbitmq \
--restart=always \
--hostname rabbitmq \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-p 5672:5672 \
-p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=portal \
-e RABBITMQ_DEFAULT_PASS=portal \
rabbitmq:3.8.7-management

# elasticsearch
docker run -d --name elasticsearch \
--restart=always \
-e ES_JAVA_OPTS="-Xms2048m -Xmx2048m" \
-e "discovery.type=single-node" \
-v /etc/localtime:/etc/localtime \
-v /etc/timezone:/etc/timezone \
-v $(pwd)/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-p 9200:9200 \
-p 9300:9300 \
elasticsearch:6.8.10

# ik分词器
docker cp elasticsearch-analysis-ik-6.8.10.zip elasticsearch:/root/elasticsearch-analysis-ik.zip
docker exec elasticsearch mkdir -p /etc/elasticsearch/plugins/ik
docker exec elasticsearch unzip -o -d /etc/elasticsearch/plugins/ik /root/elasticsearch-analysis-ik.zip
docker restart elasticsearch

# nacos
docker run -d --name nacos \
--restart always \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-e NACOS_AUTH_ENABLE=true \
-p 8848:8848 \
nacos/nacos-server:1.3.1
