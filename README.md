# Backend application MakeMark notes service

###
- Kotlin
- SPRING WEB FLUX
- GASYNC SQL
- CAFFEINE

For application need EnvFile by Borys Pierov plugin and .env file which contains:
```dotenv
POSTGRES_VERSION=14
POSTGRES_HOST=[your postgres host here]
POSTGRES_PORT=[your postgres port here]
POSTGRES_SCHEMA=mmark
POSTGRES_DB=mmark
POSTGRES_USER=[your postgres user here]
POSTGRES_PASSWORD=[your postgres password here]

LIQUIBASE_VERSION=4.11

PASSWORD_SALT=[your salt here]
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

Postman collection :
```dotenv
./postman/Mmark.postman_collection.json
```