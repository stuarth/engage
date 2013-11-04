(defproject {{name}} "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [fogus/ring-edn "0.2.0"]
                 [compojure "1.1.5"]]

  :ring {:handler {{name}}.handler/app
         :nrepl {:start? true}
         :init {{name}}.handler/init}

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[lein-cljsbuild "0.3.4"]
                                  [lein-ring "0.8.8"]
                                  [ring-mock "0.1.5"]
                                  [ring/ring-jetty-adapter "1.2.0"]
                                  [org.clojure/clojurescript "0.0-1934"]]
                   :plugins [[com.keminglabs/cljx "0.3.0"]
                             [com.cemerick/austin "0.1.1"]
                             [lein-ring "0.8.7"]]}}

  :source-paths ["target/generated/clj" "src/clj"]
  :cljsbuild
  {:builds
   [{:source-paths ["src/cljs" "target/generated/cljs"]
     :compiler
     {:pretty-print true
      :externs ["src/cljs/externs.js"]
      :output-to "resources/public/js/main.js"
      :optimizations :whitespace
      :source-map "resources/public/js/main.js.map"}}]}
  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/generated/clj"
                   :rules :clj}

                  {:source-paths ["src/cljx"]
                   :output-path "target/generated/cljs"
                   :rules :cljs}]}
  :test-paths ["test/clj"]
  :hooks [cljx.hooks])
