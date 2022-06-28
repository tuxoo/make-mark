# Backend application MakeMark notes service

###
- Kotlin
- SPRING WEB FLUX
- GASYNC SQL
- CAFFEINE

For application need EnvFile by Borys Pierov plugin and .env file which contains:
```dotenv
POSTGRES_VERSION=[your postgres version here]
POSTGRES_HOST=[your postgres host here]
POSTGRES_PORT=[your postgres port here]
POSTGRES_SCHEMA=[your postgres schema here]
POSTGRES_DB=[your postgres db here]
POSTGRES_USER=[your postgres user here]
POSTGRES_PASSWORD=[your postgres password here]

LIQUIBASE_VERSION=[your liquibase version here]

PASSWORD_SALT=[your salt here]
JWT_SIGNING_KEY=[your signing key here]
```

For running application need to build application without tests:
```dotenv
gradle build -x test
```

For running application in Docker need to build application and:
```dotenv
docker compose up
```