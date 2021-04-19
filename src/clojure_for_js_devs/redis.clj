(ns clojure-for-js-devs.redis
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [taoensso.carmine :as car]))

;; Note that the carmine "connection" is really just a configuration; the
;; library itself looks after the underlying connection pool etc.
;; See https://github.com/ptaoussanis/carmine#getting-started
(defrecord Redis [uri connection]
  component/Lifecycle
  (start [this]
    ; If there's already a connection return the instance of the Redis class
    (if (:connection this)
      this
      ; Otherwise, associate the 'connection' property of this defrecord with 
      ; a map representing the Redis connection
      (do
        (println "Starting Redis component")
        (assoc this :connection {:pool {} :spec {:uri uri}}))))

  (stop [this]
    (if (:connection this)
      (do
        (println "Stopping Redis component")
        (assoc this :connection nil))
      this)))

(defn new-redis
  "Create instance of Redis class"
  [uri]
  (map->Redis {:uri uri}))

(defn ping
  "Check that Redis connection is active"
  [redis]
  (car/wcar (:connection redis) (car/ping)))

(defn getKey
  "Retrieve count for a key in Redis DB."
  [redis key]
  (car/wcar (:connection redis) (car/get key)))

(defn incr
  "Increment count for a key in Redis DB."
  [redis key]
  (car/wcar (:connection redis) (car/incr key)))
