(ns clojure-for-js-devs.handlers-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure-for-js-devs.handlers :as handlers]
            [bond.james :as bond]
            [clojure-for-js-devs.redis :as redis]))

(deftest handlers
  (testing "hello-world-handler"
    (let [response "howdy!"]
      (is (= (handlers/hello-world-handler) response))))
  (testing "counter-handler"
    (let [response "Counter: 44"]
      (bond/with-stub! [[redis/getKey (constantly 44)] [redis/incr (constantly nil)]]
        (is (= (handlers/counter-handler {:remote-addr "1.2.3.4.5"} {}) response))))))
