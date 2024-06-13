FROM openjdk:20-jdk-alpine3.14
# ustawić tutaj utf i utc
RUN mkdir /app
COPY ./build/libs/system-storage-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]