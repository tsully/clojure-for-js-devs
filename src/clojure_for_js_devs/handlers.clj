(ns clojure-for-js-devs.handlers
  (:require [clojure-for-js-devs.redis :as redis]
            [clojure.string :as string]))

(defn get-client-ip [req]
  (if-let [ips (get-in req [:headers "x-forwarded-for"])]
    (-> ips (string/split #",") first)
    (:remote-addr req)))

(defn hello-world-handler
  []
  "howdy!")

(defn ping-handler [redis-component]
  (println "Handling ping request")
  (redis/ping redis-component))

(defn counter-handler [req redis-component]
  (let [ip (str (get-client-ip req))]
    (redis/incr redis-component ip)
    (redis/getKey redis-component ip)))
