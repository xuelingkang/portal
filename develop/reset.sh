#!/bin/bash
# 删除开发环境初始化的容器

docker stop mysql redis rabbitmq elasticsearch
docker rm mysql redis rabbitmq elasticsearch
