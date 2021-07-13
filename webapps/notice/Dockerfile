FROM registry.cn-zhangjiakou.aliyuncs.com/publicx/oracle-jdk:8u291

MAINTAINER xuelingkang

ARG JAR_FILE

COPY $JAR_FILE app.jar

COPY --from=registry.cn-zhangjiakou.aliyuncs.com/publicx/aspectjweaver:1.9.6 /aspectjweaver.jar /aspectjweaver.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8084

ENTRYPOINT ["java", "-Dsun.jnu.encoding=UTF-8", "-Dfile.encoding=utf-8", "-Djava.security.egd=file:/dev/./urandom", "-javaagent:aspectjweaver.jar", "-Xms2048m", "-Xmx2048m", "-jar", "app.jar"]

# 制作基础镜像参考 https://gitee.com/xuelingkang/images
