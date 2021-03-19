(defproject clojure-for-js-devs "0.1.0-SNAPSHOT"
  :description "Simple Clojure HTTP microservice"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"
                  [com.stuartsierra/component]
                  [compojure]
                  [ring/ring-core]
                  [ring/ring-defaults]
                  [ring/ring-jetty-adapter]
                  [ring/ring-json]
                  [ring/ring-mock]
                  [circleci/bond]
                  [clj-http-fake "1.0.3"]]]
  :main ^:skip-aot clojure-for-js-devs.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
