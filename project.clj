(defproject clojure-for-js-devs "0.1.0-SNAPSHOT"
  :description "Simple app to demonstrate how to construct Clojure microservices."
  :url "https://github.com/tsully/clojure-for-js-devs"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.stuartsierra/component "0.4.0"]
                 [compojure "1.6.1"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.8.0"]
                 [ring/ring-json "0.5.0"]
                 [ring/ring-mock "0.4.0"]
                 [circleci/bond "0.5.0"]
                 [clj-http-fake "1.0.3"]
                 [com.taoensso/carmine "3.1.0"]]
  :main ^:skip-aot clojure-for-js-devs.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[lambdaisland/kaocha "1.0.829"]
                                  [circleci/bond "0.5.0"]
                                  [ring/ring-mock]]}}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]})
