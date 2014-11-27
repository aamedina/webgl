(ns user
  (:require [weasel.repl.websocket :as ws]
            [cemerick.piggieback :as repl]
            [cljs.analyzer :as ana]
            [clojure.tools.logging :as log]
            [webgl.core :refer [cljs-files]]
            [watchtower.core :as wt
             :refer [watcher compile-watcher watcher* ignore-dotfiles watch
                     file-filter extensions on-change rate]]))

(defn compile-glsl-filewatcher []
  (watcher ["src/cgl/webgl"]
           (file-filter (extensions :cgl))
           (rate 15)
           (on-change (fn [files]
                        (doseq [file cljs-files]
                          (spit file (slurp file)))
                        files))))

(defonce cgl-watcher (compile-glsl-filewatcher))

(defn go
  []
  (repl/cljs-repl :repl-env (ws/repl-env :ip "0.0.0.0" :port 9001)))
