# User Register

![](https://shields.io/badge/Ktor-2.3.10-violet) ![](https://shields.io/badge/v1.0-purple)

Service for public API to register imager-marker users.

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

File `src/main/resources/application.conf`:

```yaml
ktor {
    deployment {
        port = 8083
        port = ${?PORT}
    }
    application {
        modules = [ com.ertools.ApplicationKt.module ]
    }
    system-storage {
        user_url = "http://127.0.0.1:8082/user"
        login_url = "http://127.0.0.1:8082/login"
        jwt_login = "system-storage-user"
        jwt_password = "system-storage-password"
        jwt_expiring = 3000000
    }
    encryption {
        key_length = 10
        illegal_chars = """\&/^$\"'"""
    }
}
```

## Private-API Endpoints

```http request
### New user
POST http://127.0.0.1:8083/register
Content-Type: application/json

{
  "name": "test1",
  "login": "login1",
  "password": "password1"
}
```

## Docker

Create fat jar:
```bash
./gradlew shadowJar
```

Create image:
```bash
docker build -t user-register-img .
```

Create container:
```bash
docker run -p 8082:8082 --name user-register user-register-img
```




