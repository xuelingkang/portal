FROM registry.cn-zhangjiakou.aliyuncs.com/publicx/oracle-jdk:8u261

MAINTAINER xuelingkang

ARG JAR_FILE

COPY $JAR_FILE app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8090

CMD java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar app.jar > /dev/null 2>&1

# 制作基础镜像参考 https://gitee.com/xuelingkang/images
