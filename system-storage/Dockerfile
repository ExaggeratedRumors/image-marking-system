FROM openjdk:20-jdk-slim
# ustawić tutaj utf i utc
# Set the working directory inside the container
#WORKDIR /app

# Copy the build.gradle.kts and settings.gradle.kts files
#COPY build.gradle.kts settings.gradle.kts gradlew /app/
#COPY gradle /app/gradle

# Copy the application source code
#COPY src /app/src

# Copy the resources
#COPY resources /app/resources

# Copy the swagger file
#COPY openapi.yaml /app/resources/openapi.yaml

# Install dependencies and build the application
#RUN ./gradlew build

# Copy the built jar file to the container
#COPY build/libs/*.jar /app/app.jar

# Expose the port that the application will run on
#EXPOSE 8082

# Set the environment variables for the application (if needed)
# ENV ENV_VAR_NAME value

RUN mkdir /app
COPY ./build/libs/system-storage-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]