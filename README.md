# MakeMark application for making some notes

###
- Kotlin
- SPRING WEB FLUX
- GASYNC SQL
- CAFFEINE

For application need EnvFile by Borys Pierov plugin and .env file which contains:

```dotenv
IP_ADDRESS=host.docker.internal

APP_PORT=[your application port here]
APP_URL=http://localhost:${APP_PORT}

API_PATH=/

POSTGRES_VERSION=14
POSTGRES_HOST=[your postgres host here]
POSTGRES_PORT=[your postgres port here]
POSTGRES_DB=mmark
POSTGRES_SCHEMA=mmark
POSTGRES_URL=jdbc:postgresql://${IP_ADDRESS}:${POSTGRES_PORT}/${POSTGRES_DB}
POSTGRES_USER=[your postgres user here]
POSTGRES_PASSWORD=[your postgres password here]

LIQUIBASE_VERSION=4.11

HASH_SALT=[your salt here]
JWT_SIGNING_KEY=[your signing key here]
```

For successfully running liquibase need to append in db/liquibase.properties:
```dotenv
username: [your postgres user here]
password: [your postgres password here]
```

For running application need to build application without tests:
```dotenv
gradle build -x test
```

For running application in Docker need to build application and:
```dotenv
docker compose up
```