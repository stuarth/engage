(ns user
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [{{name}}.handler :refer [app] :as h]
            [cemerick.austin]
            [cemerick.austin.repls]))

(def +default-config+ {:http-port 3000})

(defonce system nil)

(defn start!
  ([opts]
     (let [config (merge +default-config+ opts)]
       (h/init config)
       (alter-var-root #'system (constantly
                                 {:config config
                                  :http-server (run-jetty app {:port (:http-port config)
                                                               :join? false
                                                               :daemon? true})}))))
  ([] (start! {})))

(defn stop!
  [system]
  (.stop (:http-server system))
  (cemerick.austin/stop-server)
  (alter-var-root #'system (constantly nil)))

(defn cljs-repl! 
  ([opts]
     (when (nil? system)
       (start! opts))
     (cemerick.austin.repls/cljs-repl
      (reset! cemerick.austin.repls/browser-repl-env
              (cemerick.austin/repl-env))))
  ([] (cljs-repl! {})))


(comment
  (start! {})
  )

