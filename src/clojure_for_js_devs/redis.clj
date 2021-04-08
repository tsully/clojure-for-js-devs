(ns clojure-for-js-devs.redis
  (:gen-class)
  (:refer-clojure :exclude [get])
  (:require [com.stuartsierra.component :as component]
            [taoensso.carmine :as car]))

;; Note that the carmine "connection" is really just a configuration; the
;; library itself looks after the underlying connection pool etc.
;; See https://github.com/ptaoussanis/carmine#getting-started
(defrecord Redis [uri connection]
  component/Lifecycle

  (start [this]
    (if (:connection this)
      this
      (do
        (println "Starting Redis component")
        (assoc this :connection {:pool {} :spec {:uri uri}}))))

  (stop [this]
    (if (:connection this)
      (do
        (println "Stopping Redis component")
        (assoc this :connection nil))
      this)))

(defn new-redis [uri]
  (map->Redis {:uri uri}))

(defn ping [redis]
  (car/wcar (:connection redis) (car/ping)))

(defn getKey [redis key]
  (car/wcar (:connection redis) (car/get key)))

(defn incr [redis key]
  (car/wcar (:connection redis) (car/incr key)))
