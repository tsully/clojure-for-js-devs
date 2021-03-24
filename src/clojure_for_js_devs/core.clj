(ns clojure-for-js-devs.core
  (:gen-class
   :main true)
  (:require
   [clojure.tools.logging :as log]
   [com.stuartsierra.component :as component]
   [clojure-for-js-devs.http :as http]
   [clojure-for-js-devs.redis :as redis]))

(defn main-system []
  (component/system-map
   ; TODO move redis URI, http port, and IP address to config.edn file
   ; TODO create function to get set and get config vars
   :redis (redis/new-redis "redis://redis:6379")
   :http-server (component/using
                 (http/new-server "0.0.0.0" 8105)
                 [:redis])))

(defn start
  [system]
  (try
    (component/start system)
    (catch Exception ex
      (log/error ex "Failed to start the system"))))

(defn stop
  [system]
  (component/stop system))

(defonce ^:dynamic *system* nil)

(defn -main []
  (let [system (start (main-system))]
    (alter-var-root #'*system* (constantly system))
    (.addShutdownHook (Runtime/getRuntime) (Thread. (partial stop system)))))
