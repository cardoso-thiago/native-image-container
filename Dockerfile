FROM ghcr.io/graalvm/native-image-community:21 AS builder

RUN microdnf install -y \
    wget \
    tar \
    && microdnf clean all

WORKDIR /app

RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz && \
    tar -xvzf apache-maven-3.9.9-bin.tar.gz && \
    mv apache-maven-3.9.9 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/local/bin/mvn

WORKDIR /app
COPY . /app

ENV AWS_REGION=sa-east-1
ENV AWS_ACCESS_KEY_ID=dummy
ENV AWS_SECRET_ACCESS_KEY=dummy

RUN mvn -Pnative native:compile -Dnative-image.options=-H:ConfigurationFileDirectories=./graal-hints

FROM ubuntu:22.04

ENV AWS_REGION=sa-east-1
ENV AWS_ACCESS_KEY_ID=dummy
ENV AWS_SECRET_ACCESS_KEY=dummy

COPY --from=builder /app/target/native-image-container /app/native-image-container

WORKDIR /app

EXPOSE 8081
CMD ["./native-image-container"]
