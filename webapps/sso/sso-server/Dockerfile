FROM registry.cn-zhangjiakou.aliyuncs.com/publicx/oracle-jdk:8u291

MAINTAINER xuelingkang

ARG JAR_FILE

COPY $JAR_FILE app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8081

ENTRYPOINT ["java", "-Dsun.jnu.encoding=UTF-8", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-Xms2048m", "-Xmx2048m", "-jar", "app.jar"]

# 制作基础镜像参考 https://gitee.com/xuelingkang/images
