(ns clojure-for-js-devs.http
  (:require
   [com.stuartsierra.component :as component]
   [compojure.core :refer [GET POST routes]]
   [compojure.route :as route]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :as ring-defaults]
   [ring.middleware.json :as ring-json]
   [clojure-for-js-devs.handlers :as http-handlers]))

(defn app-routes []
  (routes
   (GET "/hello-world" [] "Howdy!")
   ; TODO define new endpoints
;;    (GET "/user-attributes" [user_analytics_id]
;;      (http-handlers/get-growth-user-attributes user_analytics_id))
    ;; Override the default fallthrough route.
   (route/not-found "Not found")))

(defn start
  "Start the HTTP server with our routes and middlewares."
  ([host port]
   (-> (app-routes)
    ;;    (auth-middleware)
       ;; Catch any exceptions in handlers.
       ;; TODO see how circleci.backplane.exceptions.ring works and whether we should create simple version of that handler here
       ;; (ring-exceptions/wrap-report-exceptions)
       ;; Turn map response bodies into JSON with the correct headers.
       (ring-json/wrap-json-response)
       ;; Turn JSON into map
       (ring-json/wrap-json-body {:keywords? true})
       ;; Parse query strings and set default response headers.
       (ring-defaults/wrap-defaults ring-defaults/api-defaults)
       (jetty/run-jetty {:host host
                         :port port
                         :join? false}))))

(defrecord WebServer [host port]
  component/Lifecycle
  (start [component]
    (let [server (start host port)]
      (assoc component :server server)))
  (stop [component]
    (when-let [server (:server component)]
      (.stop server))
    ; TODO double check this dissoc works correctly
    (dissoc component :server)))

(defn new-server [host port]
  (->WebServer host port))
