# User Service

User service is a microservice in java spring boot for register of users.

## How to run it ?

It runs with docker. so, you write the docker's commands.

The docker's command build imagen to be: 

``` bash
docker build -t userService:latest .
```
The docker's command run imagen to be:


``` bash
docker run -p8080:8080 userService
```
## Http Restful Request

### Post

url = localhost:8080/users

#### Request

bodyJson

```Json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter52",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "contryCode": "57"
        }
    ]
}
```

#### Response

```Json
{
    "userId": "da3647de-2bfb-4cc9-ba72-4dcf1ec6f4d7",
    "create": "2023-04-03T15:14:19.390+0000",
    "modified": "2023-04-03T15:14:19.390+0000",
    "lastLogin": "2023-04-03T15:14:19.390+0000",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODA1MzQ4NTksImV4cCI6MTY4MDUzNTE1OX0.TTbQwZvVX2TFJyytNulq-Ay1Xy-hmYGzABHSlQdoMKM",
    "active": true
}
```

### Get

localhost:8080/users

#### Request

Headers

token = token generated in response post

user_id = user's id

#### Response

List of users 

```Json
[
    {
        "userId": "da3647de-2bfb-4cc9-ba72-4dcf1ec6f4d7",
        "create": "2023-04-03T00:00:00.000+0000",
        "modified": "2023-04-03T00:00:00.000+0000",
        "lastLogin": "2023-04-03T00:00:00.000+0000",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODA1MzQ4NTksImV4cCI6MTY4MDUzNTE1OX0.TTbQwZvVX2TFJyytNulq-Ay1Xy-hmYGzABHSlQdoMKM",
        "active": true
    },
    {
        "userId": "f38a79ac-a755-4e77-a93f-657269fad786",
        "create": "2023-04-03T00:00:00.000+0000",
        "modified": "2023-04-03T00:00:00.000+0000",
        "lastLogin": "2023-04-03T00:00:00.000+0000",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODA1MzU0MTcsImV4cCI6MTY4MDUzNTcxN30.iQ09G6poK5ldD-SnU90jhraxeOgOF_j7I3I7gXintzs",
        "active": true
    }
]
```

### PUT

localhost:8080/users

#### Request

Headers

token = token generated in response post

user_id = user's id

BodyJson

```Json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "contryCode": "57"
        }
    ],
    "active":false
}
```

#### Response

```Json
{
    "userId": "da3647de-2bfb-4cc9-ba72-4dcf1ec6f4d7",
    "create": "2023-04-03T15:14:19.390+0000",
    "modified": "2023-04-03T15:20:19.390+0000",
    "lastLogin": "2023-04-03T15:14:19.390+0000",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODA1MzQ4NTksImV4cCI6MTY4MDUzNTE1OX0.TTbQwZvVX2TFJyytNulq-Ay1Xy-hmYGzABHSlQdoMKM",
    "active": false
}
```

### Delete

#### Request

Headers

token = token generated in response post

user_id = user's id

#### Response

```Json
{
    "mensaje": "Usuario Eliminado"
}
```

## Diagram


https://drive.google.com/file/d/1EZeRGlOWLHmLsp6NXzTHpYKG_1MSG73d/view?usp=sharing
