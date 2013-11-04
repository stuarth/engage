(ns leiningen.new.engage
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.java.io]))

(def render (renderer "engage"))

(defn engage
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' engage project.")
   
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["dev/user.clj" (render "user.clj" data)]
             ["resources/public/index.html" (render "index.html" data)]
             [(str "src/clj/" (:sanitized data) "/handler.clj") (render "handler.clj" data)]
             [(str "src/cljs/" (:sanitized data) "/start.cljs") (render "start.cljs" data)])
    
    (doto (clojure.java.io/file (str (:sanitized data) "/target/generated/cljs/"))
      clojure.java.io/make-parents
      .mkdir)))
