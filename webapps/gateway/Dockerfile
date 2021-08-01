FROM registry.cn-zhangjiakou.aliyuncs.com/publicx/oracle-jdk:8u291

MAINTAINER xuelingkang

ARG JAR_FILE

RUN mkdir -p /app
WORKDIR /app

COPY --from=registry.cn-zhangjiakou.aliyuncs.com/publicx/gosu-amd64:1.13 /gosu /usr/bin/gosu
COPY docker-entrypoint.sh /usr/bin/docker-entrypoint.sh
COPY $JAR_FILE app.jar

RUN chmod u+x /usr/bin/gosu /usr/bin/docker-entrypoint.sh && \
    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8000

ENTRYPOINT ["java", "-Dsun.jnu.encoding=UTF-8", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-Xms2048m", "-Xmx2048m", "-jar", "app.jar"]

