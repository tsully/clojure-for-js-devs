(ns clojure-for-js-devs.handlers
  (:require [clojure-for-js-devs.redis :as redis]))

(defn hello-world-handler
  []
  "howdy!")

(defn ping-handler [redis-component]
  (println "Handling ping request")
  (redis/ping redis-component))

(defn counter-handler [req redis-component]
  (let [ip (:remote-addr req)
        counter (redis/getKey redis-component ip)]
    (redis/incr redis-component ip)
    (str "Counter: " counter)))
