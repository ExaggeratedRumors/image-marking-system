# Image Marker

![](https://shields.io/badge/Ktor-2.3.10-violet) ![](https://shields.io/badge/v1.0-purple)

Web application processing and coding images by private user code. 

## Release

`
version 1.0
`

## Technologies

- Kotlin 1.9.23
- Ktor 2.3.10

## Requirements

- JDK 20
- Gradle 8.4

## Configuration

Set up application properties in `configuration.yaml` file in directory `src/main/resources` and fill with content:
```yaml
ktor {
  deployment {
      port = 8081
      port = ${?PORT}
  }
  application {
      modules = [ com.ertools.ApplicationKt.module ]
  }
  system-storage {
      user_url = "http://127.0.0.1:8082/user"
      login_url = "http://127.0.0.1:8082/login"
      image_url = "http://127.0.0.1:8082/image"
      jwt_login = "system-storage-user"
      jwt_password = "system-storage-password"
      jwt_expiring = 3000000
  }
  key {
      size = 10
      opacity = 0.1
  }
}
``


## Public-API Endpoints
```http request
GET http://localhost:8081/{image}
```

Create fat jar:
```bash
./gradlew shadowJar
```

Create storage:
```bash
docker volume create image-data
```

Create image:
```bash
docker build -t image-marker-img .
```

Create container:
```bash
docker run -p 8081:8081 --name image-marker image-marker-img
```




