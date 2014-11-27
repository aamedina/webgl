(ns webgl.core
  (:require [dommy.core :refer-macros [sel1]])
  (:require-macros [webgl.core :refer [shader-material]]))

(defn -main
  []
  (let [canvas (sel1 :#app)]
    (js/console.log canvas)))
