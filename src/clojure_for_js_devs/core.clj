(ns clojure-for-js-devs.core
  (:gen-class
   :main true)
  (:require [circleci.backplane.init :as backplane-init]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [clojure-for-js-devs.http :as http]))

(defn main-system []
  (component/system-map
   :http-server (component/using
                 ; TODO create function to get set and get config vars
                 (http/new-server "0.0.0.0" 8105)
                 {:growth-service-client :growth-service-client})))

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
  (backplane-init/init)
  (let [system (start (main-system))]
    (alter-var-root #'*system* (constantly system))
    (.addShutdownHook (Runtime/getRuntime) (Thread. (partial stop system)))))
