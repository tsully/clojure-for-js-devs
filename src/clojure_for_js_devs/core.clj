(ns clojure-for-js-devs.core
  (:gen-class
   :main true)
  (:require
   [com.stuartsierra.component :as component]
   [clojure-for-js-devs.http :as http]
   [clojure-for-js-devs.redis :as redis]))

(defn main-system
  "Creates map of component dependencies with implementation of Lifecycle protocol"
  []
  (component/system-map
   :redis (redis/new-redis "redis://redis:6379")
   :http-server (component/using
                 (http/new-server "0.0.0.0" 8080)
                 [:redis])))

(defn start
  "Start components of system in dependency order. Runs SystemMap implementation of Lifecycle protocol's 'start' function"
  [system]
  (try
    (component/start system)
    (catch Exception _ex
      (println "Failed to start the system"))))

(defn stop
  "Stop components of system in dependency order. Runs SystemMap implementation of Lifecycle protocol's 'stop' function"
  [system]
  (component/stop system))

; Create a variable called *system* that can only be defined once
; ^:dynamic means that this variable can be rebound to a new value
(defonce ^:dynamic *system* nil)

(defn -main
  "Entry point to the application."
  []
  (let [system (start (main-system))]
    ; dynamically rebind *system* to the newly created SystemMap instance
    (alter-var-root #'*system* (constantly system))
    ; Create hook that stops the component system in a controlled manner before the JVM completely shuts down 
    (.addShutdownHook (Runtime/getRuntime) (Thread. (partial stop system)))))
