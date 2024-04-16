# Image Marker

![](https://shields.io/badge/Ktor-2.3.10-violet) ![](https://shields.io/badge/v0.1-purple)

Web application processing and coding images by private user code. 

## Release

`
version 0.1
`

## Technologies

- Kotlin 1.9.23
- Ktor 2.3.10

## Requirements

- JDK 11
- Gradle 8.4

## Configuration

Set up application properties in `configuration.yaml` file in directory `src/main/resources` and fill with content:
```yaml
keyLength: 10
keySize: 10
keyOpacity: 0.1
```

## Routing

Route by:
```http request
GET http://localhost:8081/{image}
```

## Image

Create image:
```bash
docker build -t image-marker-img .
```

## Container

Create container:
```bash
docker run -p 8081:8081 --name image-marker image-marker-img
```




