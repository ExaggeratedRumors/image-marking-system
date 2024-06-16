FROM openjdk:20-jdk-slim
RUN mkdir /app
COPY ./build/libs/user-register-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]