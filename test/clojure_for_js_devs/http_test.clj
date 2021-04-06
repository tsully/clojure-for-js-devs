(ns clojure-for-js-devs.http-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-http.client :as client]
            [com.stuartsierra.component :as component]
            [clojure-for-js-devs.http :as http]))

(defn simple-response
  "Extract only the things we care about from the response so that we can assert
  on the entire map at once, which makes test failures easier to debug in a
  single pass."
  [response]
  (select-keys response [:status :body]))

(def ephemeral-port
  "Bind to an emphemeral post that the OS chooses to prevent bind conflicts
  between multiple test runs."
  0)

(deftest routes
  (let [system (component/start
                (component/system-map
                 :http-server (http/new-server "localhost" ephemeral-port)))
        url (.getURI (:server (:http-server system)))]
    (try
      (testing "GET /hello-world"
        (let [response (client/get (str url "/hello-world"))]
          (is (= {:status 200
                  :body "howdy!"}
                 (simple-response response)))))
      (finally (component/stop system)))))
