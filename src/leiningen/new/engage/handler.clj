(ns {{name}}.handler
  (:require [compojure
             [core :refer [defroutes GET]]
             [route :as r]
             [handler :as h]]
            [ring.util.response :refer [redirect]]
            [ring.middleware.edn :refer [wrap-edn-params]]
            [cemerick.austin.repls :as cljs-repl]))

(def config {:env :prod})

(defroutes routes
  (r/resources "/")
  (GET "/" [] (redirect "index.html"))
  (GET "/browser-repl.js" {} (cljs-repl/browser-connected-repl-js))
  (r/not-found "404"))

(def app
  (-> #'routes
      h/site
      wrap-edn-params))

(defn init
  ([]
     (init {}))
  ([opts]
     (alter-var-root #'config merge opts)))
