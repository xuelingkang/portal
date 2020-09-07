#!/bin/bash
# 删除开发环境初始化的容器

docker stop elasticsearch rabbitmq redis mysql
docker rm elasticsearch rabbitmq redis mysql
