(ns webgl.core
  (:refer-clojure :exclude [compile])
  (:require [clojure-gl.compiler :refer [compile-ns]]            
            [clojure.tools.logging :as log]
            [cljs.analyzer :as ana]))

(defonce cljs-files #{})

(defmacro compile
  [shader-ns]
  (when-not (contains? cljs-files ana/*cljs-file*)
    (alter-var-root #'cljs-files conj ana/*cljs-file*))
  (compile-ns shader-ns))

(defmacro shader-material
  [vertex fragment opts]
  `(THREE.ShaderMaterial. (doto ~opts
                            (aset "vertexShader" (compile ~vertex))
                            (aset "fragmentShader" (compile ~fragment)))))
