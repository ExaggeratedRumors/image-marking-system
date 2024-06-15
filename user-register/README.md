# User Register

![](https://shields.io/badge/Ktor-2.3.10-violet) ![](https://shields.io/badge/v0.1-purple)

Service for public API to register imager-marker users.

## Release

`
version 0.1
`

## Technologies

- Kotlin 1.9.23
- Ktor 2.3.10

## Requirements

- JDK 20
- Gradle 8.4

## Configuration

3. Run application.
4. Visit Swagger UI:
```http request
http://127.0.0.1:8083/swagger-ui
```
5. Authorize application by `/login` endpoint:
```json
{
  "username": "system-storage-user",
  "password": "system-storage-password"
}
```
6. Copy JWT from response body:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgyL3N0b3JhZ2UtYXVkaWVuY2UiLCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgyLyIsInVzZXJuYW1lIjoic3lzdGVtLXN0b3JhZ2UtdXNlciIsImV4cCI6MTcxODMzOTU3N30.8A6nwCZtAQjWCRbVJpKxHZhNg_26EJqhcBuaOKwysxA"
}
```
7. Paste JWT in Authorize section to finalize authorization.

## Private-API Endpoints

```http request
GET http://localhost:8082/user/{login}
Accept: application/json

###
POST http://127.0.0.1:8082/user
Content-Type: application/json

{
  "name": "UserName",
  "login": "UserLogin",
  "password": "UserPassword",
  "code": "UserCode"
}

###
DELETE http://127.0.0.1:8082/user/{login}

###
PATCH http://127.0.0.1:8082/user/{login}

```

## Docker

Create fat jar:
```bash
./gradlew shadowJar
```

Create image:
```bash
docker build -t system-storage-img .
```

Create container:
```bash
docker run -p 8082:8082 --name system-storage system-storage-img
```




