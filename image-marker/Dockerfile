FROM openjdk:17-jdk-alpine3.14
ENV DB_URL jdbc:mysql://localhost:3306/mydatabase
ENV DB_USER myuser
ENV DB_PASSWORD mypassword
RUN mkdir /app
COPY ./build/libs/image-marker-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]