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
  "Create system map to be used in integration tests."
  []
  (component/system-map
   :redis (redis/new-redis "redis://localhost:6379")
   :http-server (component/using
                 (http/new-server "localhost" ephemeral-port)
                 {:redis :redis})))

(defn- setup-system
  "Rebind system variable to the system map."
  []
  (alter-var-root #'system (fn [_] (component/start (test-system)))))

(defn- tear-down-system
  "Stop each component in the system map and bind result to the system variable."
  []
  (alter-var-root #'system (fn [s] (when s (component/stop s)))))

(defn init-system
  "Create the test component system map, run the tests, and then tear down the system map."
  [test-fn]
  (setup-system)
  (test-fn)
  (tear-down-system))
