This project is intended as an educational tool to teach JavaScript developers how to set up their first
Clojure microservice.

`clojure-for-js-devs` is a simple Clojure microservice that counts how many times the user visits the `/counter` route.

### Run the REPL

The REPL will be the workhorse of your development workflow. Clojure’s REPL allows you an interactive and flexible development experience that ultimately tightens your iteration/feedback cycles.

Before you start the REPL, run docker-compose to set up the Redis server.

```sh
docker-compose -f docker-compose-services.yml up
```

Fire up the REPL:

```sh
lein repl
```

Now, let's actually create our system component map, which includes both our HTTP server and Redis client:

```clojure
clojure-for-js-devs.core=> (-main)
```

To check that our HTTP server is up and running, navigate to `http://localhost:8080/counter`. Each time you refresh the page, the counter will increment.

You can now dynamically interact with your application. For example, you can stop your system component map by running:

```clojure
clojure-for-js-devs.core=> (stop *system*)
```

Your REPL starts in your `clojure-for-js-devs.core` namespace, but you can work with any other namespace.

```clojure
; To make the clojure-for-js-devs.handlers namespace available under the "handlers" alias
clojure-for-js-devs.core=> (require '[clojure-for-js-devs.handlers :as handlers])
; To switch to the handlers namepsace
clojure-for-js-devs.core=> (in-ns 'clojure-for-js-devs.handlers)
```

Please note that changes you make to your project files after you begin the REPL will not be immediately visible in your REPL. Reloading code into your REPL is beyond the scope of this README, but [Calva](https://calva.io/) (for VSCode) and [Cursive](https://cursive-ide.com/userguide/repl.html) offer handy utility commands to easily load code changes into your REPL.

### Run tests

Our test suite has integration tests that check that a HTTP request will properly update our Redis datastore. So to run our tests, we first need to start the local Redis server.

```sh
docker-compose -f docker-compose-services.yml up
```

In a separate terminal, run the tests:

```sh
lein test
```

You can run individual tests by specifying the namespace and testing function. For example:

```sh
lein test --focus clojure-for-js-devs.handlers-test/handlers-hello-world
```

### Run the project without the REPL

During local development, you should generally be running your application from the REPL, but occasionally you may want to quickly start the application without needing to manually run `(-main)` in the REPL to create your component system map.

First, run docker-compose to set up the Redis server.

```sh
docker-compose -f docker-compose-services.yml up
```

In a separate terminal, start the clojure-for-js-devs service.

```sh
lein run
```

`lein run` will automatically runs your `(-main)` to create your component system map, starting your HTTP server and Redis client.

### Build the app for a production environment

When you deploy your application to production, you'll compile it to a standalone JAR file. Typically, this will be handled by your CI, but to understand how this works, you can compile your project into a JAR file and run it locally.

```sh
lein uberjar
```

Next, make sure that your Redis server is running:

```sh
docker-compose -f docker-compose-services.yml up
```

Finally, run the JAR file:

```sh
java -jar target/uberjar/clojure-for-js-devs-0.1.0-SNAPSHOT-standalone.jar
```
