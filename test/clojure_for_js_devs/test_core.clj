(ns clojure-for-js-devs.test-core
  (:require [com.stuartsierra.component :as component]
            [clojure-for-js-devs.http :as http]
            [clojure-for-js-devs.redis :as redis]))

(def system nil)

(def ephemeral-port
  "Bind to an emphemeral post that the OS chooses to prevent bind conflicts
  between multiple test runs."
  0)

(defn- test-system
  []
  (component/system-map
   :redis (redis/new-redis (str "redis://redis:" "6379"))
   :http-server (component/using
                 (http/new-server "localhost" ephemeral-port)
                 {:redis :redis})))

(defn- setup-system
  []
  (alter-var-root #'system (fn [_] (component/start (test-system)))))

(defn- tear-down-system
  []
  (alter-var-root #'system (fn [s] (when s (component/stop s)))))

(defn init-system
  [test-fn]
  (setup-system)
  (test-fn)
  (tear-down-system))
