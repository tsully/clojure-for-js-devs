### Using Docker

Run the requires services

```sh
docker-compose -f docker-compose-services.yml up
```

In a separate terminal, start growth-service

```sh
lein run
```

Navigate to `http://localhost:8105/hello-world`
