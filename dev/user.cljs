(ns user
  (:require [figwheel.client :as fw]
            [weasel.repl :as repl]
            [webgl.examples :refer [-main]]))

(enable-console-print!)

(fw/watch-and-reload
  :jsload-callback (fn [] (-main)))

(when-not (repl/alive?)
  (repl/connect "ws://localhost:9001"))

(-main)
