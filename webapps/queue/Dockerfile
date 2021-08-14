FROM registry.cn-zhangjiakou.aliyuncs.com/publicx/oracle-jdk:8u291

MAINTAINER xuelingkang

ARG JAR_FILE

RUN mkdir -p /app /var/log/portal/queue
WORKDIR /app

COPY --from=registry.cn-zhangjiakou.aliyuncs.com/publicx/aspectjweaver:1.9.6 /aspectjweaver.jar aspectjweaver.jar
COPY --from=registry.cn-zhangjiakou.aliyuncs.com/publicx/gosu-amd64:1.13 /gosu /usr/bin/gosu
COPY docker-entrypoint.sh /usr/bin/docker-entrypoint.sh
COPY $JAR_FILE app.jar

RUN chmod u+x /usr/bin/gosu /usr/bin/docker-entrypoint.sh && \
    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8085

ENTRYPOINT ["java", "-Dsun.jnu.encoding=UTF-8", "-Dfile.encoding=utf-8", "-Djava.security.egd=file:/dev/./urandom", "-javaagent:aspectjweaver.jar", "-Xms2048m", "-Xmx2048m", "-jar", "app.jar"]

