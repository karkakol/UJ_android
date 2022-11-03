FROM ubuntu:22.04

RUN apt-get update && apt-get install -y openjdk-8-jdk

RUN apt-get -y install kotlin

RUN apt-get update && apt-get -y install gradle 7.5.1

RUN apt update && apt install tzdata -y

ENV TZ="Europe/Warsaw"

RUN apt install software-properties-common -y

RUN add-apt-repository ppa:deadsnakes/ppa -y

RUN apt update

RUN apt install  -y python3.8

RUN apt-get -y install libxerial-sqlite-jdbc-java


CMD mkdir /app

WORKDIR /app

COPY build.gradle .

CMD ["gradle","helloWorld"]