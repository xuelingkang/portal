FROM debian:10.5

COPY jdk-8u261-linux-x64.tar.gz /

RUN tar -xzf /jdk-8u261-linux-x64.tar.gz

FROM debian:10.5

MAINTAINER xuelingkang

COPY --from=0 /jdk1.8.0_261 /opt/jdk1.8.0_261

RUN ln -sf /opt/jdk1.8.0_261 /opt/jdk

ENV JAVA_VERSION=8u261
ENV JAVA_HOME=/opt/jdk
ENV JRE_HOME=$JAVA_HOME/jre
ENV CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib
ENV PATH=$JAVA_HOME/bin:$PATH

# docker build -t registry.cn-zhangjiakou.aliyuncs.com/xzixi/oracle-jdk:8u261 .
# docker push registry.cn-zhangjiakou.aliyuncs.com/xzixi/oracle-jdk:8u261
