(ns clojure-for-js-devs.http-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-http.client :as client]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]))

(defrecord TestServer [host port]
  component/Lifecycle
  (start [component]
    (let [server (jetty/run-jetty host port)]
      (assoc component :server server)))
  (stop [component]
    (when-let [server (:server component)]
      (.stop server))
    (dissoc component :server)))

(defn new-test-server [host port]
  (->TestServer host port))

(def ephemeral-port
  "Bind to an emphemeral post that the OS chooses to prevent bind conflicts
  between multiple test runs."
  0)

(deftest routes
  (let [system (component/start
                (component/system-map
                 :http-server (new-test-server "localhost" ephemeral-port)))
        url (.getURI (:server (:http-server system)))]
    (try
      (testing "GET /hello-world"
        (let [response (client/get (str url "/hello-world"))]
          (is (= {:status 200
                  :body {:user_analytics_id "2043C178-2C12-42F6-A30C-3168FEE41363"
                         :role "dev ops"
                         :use_case "testing"
                         :eng_size "51"}}
                 response))))
      (finally (component/stop system)))))
