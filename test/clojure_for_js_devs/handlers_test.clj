(ns clojure-for-js-devs.handlers-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure-for-js-devs.handlers :as handlers]
            [bond.james :as bond]
            [clojure-for-js-devs.redis :as redis]
            [ring.mock.request :as ring-mock]))

(deftest handlers-hello-world
  (testing "hello-world-handler"
    (let [response "howdy!"]
      (is (= (handlers/hello-world-handler) response)))))

(deftest handlers-counter
  (testing "counter-handler"
    (let [response "Counter: 44"
          req (->  (ring-mock/request :get "/counter"))]
      (bond/with-stub! [[redis/getKey (constantly 44)] [redis/incr (constantly nil)]]
        (is (= (handlers/counter-handler req {}) response))))))


