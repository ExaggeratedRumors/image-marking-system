## Image Marking System

![Android support](https://shields.io/badge/Platform-Docker-blue) ![Android support](https://shields.io/badge/v.1.0-cyan)

<p align="center">
    <img src="images/logo.png" width="200" alt="logo"/> 
</p>

System marking images by user code.

## Diagram

<p align="center">
    <img src="images/diagram.png" width="400" alt="diagram"/> 
</p>

## Architecture

System contains 5 containers:

### system-client

Web client in ReactJS technology with features:
- Register user.
- Upload new image.
- Download marked image.

### system-database

MongoDB with 2 collections:
- Registered users
- Uploaded images


### system-storage

Ktor service communicating other services with database by private-API.

### user-register

Ktor service registering users and assign to them special code.

### image-marker

Ktor service marking images by users private code.


## Build docker containers

```docker
docker compose up
```

## Preview

<p align="center">
    <img src="images/preview1.png" width="400" alt="preview1"/> 
</p>


<p align="center">
    <img src="images/preview2.png" width="400" alt="preview2"/> 
</p>
