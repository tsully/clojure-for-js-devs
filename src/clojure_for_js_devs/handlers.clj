(ns clojure-for-js-devs.handlers
  (:require [clojure-for-js-devs.redis :as redis]))

(defn hello-world-handler
  "To check that HTTP server is working."
  []
  "howdy!")

(defn ping-handler
  "To check that HTTP server can interface with Redis."
  [redis-component]
  (println "Handling ping request")
  (redis/ping redis-component))

(defn counter-handler 
  "Increment count of times that IP address has hit endpoint and return count."
  [req redis-component]
  (let [ip (:remote-addr req)
        counter (redis/getKey redis-component ip)]
    (redis/incr redis-component ip)
    (str "Counter: " counter)))
