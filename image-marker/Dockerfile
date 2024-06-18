FROM openjdk:20-jdk-slim

RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libfreetype6-dev \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /app
COPY ./build/libs/image-marker-all.jar /app/app.jar
RUN mkdir -p /data/images
ENTRYPOINT ["java","-jar","/app/app.jar"]