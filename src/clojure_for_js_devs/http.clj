(ns clojure-for-js-devs.http
  (:require
   [com.stuartsierra.component :as component]
   [compojure.core :refer [GET POST routes]]
   [compojure.route :as route]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :as ring-defaults]
   [ring.middleware.json :as ring-json]
   [clojure-for-js-devs.handlers :as http-handlers]))

(defn app-routes [redis-component]
  (routes
   (GET "/hello-world" [] (http-handlers/hello-world-handler))
   (GET "/ping" [] (http-handlers/ping-handler redis-component))
    ;; Override the default fallthrough route.
   (route/not-found "Not found")))

(defn start
  "Start the HTTP server with our routes and middlewares."
  ([host port redis-component]
   (-> (app-routes redis-component)
       ;; Turn map response bodies into JSON with the correct headers.
       (ring-json/wrap-json-response)
       ;; Turn JSON into collection
       (ring-json/wrap-json-body {:keywords? true})
       ;; Parse query strings and set default response headers.
       (ring-defaults/wrap-defaults ring-defaults/api-defaults)
       (jetty/run-jetty {:host host
                         :port port
                         :join? false}))))

(defrecord WebServer [host port]
  component/Lifecycle
  (start [this]
    (if (:server this)
      this
      (let [server (start host port (:redis this))]
        (assoc this :server server))))
  (stop [this]
    (when-let [server (:server this)]
      (.stop server))
    (dissoc this :server)))

(defn new-server [host port]
  (->WebServer host port))
