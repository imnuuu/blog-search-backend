FROM docker-hub.coupang.net/common_frameworks/ubuntu/18.04/jdk11:latest

MAINTAINER IT Innovation<ITInnovation@coupang.onmicrosoft.com>

RUN mkdir -p /pang/program/bootapp
RUN apt-get update && apt-get -y install curl jq

WORKDIR /pang/program/bootapp
ADD setenv.sh setenv.sh
ADD build/libs/newist-api*.jar app.jar

ENV SERVER_PORT 8084
ENV SPRING_PROFILE aws
EXPOSE 8084

CMD ["/bin/bash", "-c", "source setenv.sh && exec java $JAVA_OPTS -jar app.jar >> /pang/logs/bootapp/api.log 2>&1"]
