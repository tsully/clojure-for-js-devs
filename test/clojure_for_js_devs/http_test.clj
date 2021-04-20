(ns clojure-for-js-devs.http-test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [clj-http.client :as client]
            [clojure-for-js-devs.test-core :as t]))

(defn simple-response
  "Extract only the things we care about from the response so that we can assert
  on the entire map at once, which makes test failures easier to debug in a
  single pass."
  [response]
  (select-keys response [:status :body]))

(use-fixtures :once t/init-system)

(deftest routes
  (let [url (.getURI (:server (:http-server t/system)))]
    (testing "GET /hello-world"
      (let [response (client/get (str url "hello-world"))]
        (is (= {:status 200
                :body "howdy!"}
               (simple-response response)))))
    (testing "GET /counter"
      (let [response (client/get (str url "counter"))]
        (is (= 200
               (:status (simple-response response))))))))
