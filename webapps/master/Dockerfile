FROM registry.cn-zhangjiakou.aliyuncs.com/publicx/oracle-jdk:8u261

MAINTAINER xuelingkang

ARG JAR_FILE

COPY $JAR_FILE app.jar

COPY --from=registry.cn-zhangjiakou.aliyuncs.com/publicx/aspectjweaver:1.9.6 /aspectjweaver.jar /aspectjweaver.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8087

CMD java -Dfile.encoding=utf-8 -Djava.security.egd=file:/dev/./urandom -javaagent:aspectjweaver.jar $JAVA_OPTS -jar app.jar > /dev/null 2>&1

# 制作基础镜像参考 https://gitee.com/xuelingkang/images
