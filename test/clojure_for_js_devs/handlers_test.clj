(ns clojure-for-js-devs.handlers-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure-for-js-devs.handlers :as handlers]))

(deftest handlers
  (testing "hello-world-handler"
    (let [response "howdy!"]
      (is (= (handlers/hello-world-handler) response)))))
