(ns clojure-for-js-devs.handlers
  (:require [clojure-for-js-devs.redis :as redis]))

(defn hello-world-handler
  []
  "howdy!")

(defn ping-handler [redis-component]
  (println "Handling ping request")
  (redis/ping redis-component))
