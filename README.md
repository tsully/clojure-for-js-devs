This project is intended as an educational tool to teach JavaScript developers how to set up their first microservice. clojure-for-js-devs is a simple microservice that counts how many times the user visits the `/counter` route.

### Run the project

First, run docker-compose to set up the Redis server.

```sh
docker-compose -f docker-compose-services.yml up
```

In a separate terminal, start the clojure-for-js-devs service.

```sh
lein run
```

Navigate to `http://localhost:8080/counter`. Each time you refresh the page, the counter will increment.

### Run tests

First, start the Redis server.

```sh
docker-compose -f docker-compose-services.yml up
```

In a separate terminal, run the tests:

```sh
lein run
```

You can run individual tests by specifying the namespace and testing function. For example:

```sh
lein test --focus clojure-for-js-devs.handlers-test/handlers-hello-world
```
