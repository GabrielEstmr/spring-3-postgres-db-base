# App-Account-Challenger

## Requirements:

- Java 17
- Docker

## Banch New Feature (Balance:)

- feat/code-challenger

## How to Run:

1. Go to `account` folder:

```command
cd accounts
```

2. Run the commando to build the project (java 17 needed)

```command
mvn clean install -U -X
```

3. Go back to the main folder:

```command
cd ..
```

4. Run the application with its dependencies through docker:

```command
docker-compose up --build -d
```

5. Wait a few seconds up to be able to access the local port below:

```command
http://localhost:8080/swagger-ui/index.html#/
```

6. Distributed Tracing can be analysed at:

```command
http://localhost:9411/zipkin/
```

7. Metrics can be analysed at:

```command
http://localhost:9090/
```

8. Grafana Can be seen at:
    - username: admin
    - password: admin

```command
http://localhost:3000/
```


