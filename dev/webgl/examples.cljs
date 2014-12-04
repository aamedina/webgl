(ns webgl.examples
  (:require [dommy.core :as d :refer-macros [sel1]]
            [goog.events :as e]
            [goog.events.EventType :as et]
            [goog.style :as s]
            [goog.webgl :as gl]
            [webgl.core :as webgl]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.vec.vec2f :as vec2]
            [goog.vec.vec3f :as vec3]
            [goog.vec.vec4f :as vec4]
            [goog.vec.mat3f :as mat3]
            [goog.vec.mat4f :as mat4])
  (:require-macros [webgl.core :as gl]))

(def app-state (atom {:width js/window.innerWidth
                      :height js/window.innerHeight}))

(defonce vertex-colors
  (js/Float32Array. #js [0.0,  0.5,  -0.4,  0.4,  1.0,  0.4,
                         -0.5, -0.5,  -0.4,  0.4,  1.0,  0.4,
                         0.5, -0.5,  -0.4,  1.0,  0.4,  0.4,    
                         0.5,  0.4,  -0.2,  1.0,  0.4,  0.4,
                         -0.5,  0.4,  -0.2,  1.0,  1.0,  0.4,
                         0.0, -0.6,  -0.2,  1.0,  1.0,  0.4,
                         0.0,  0.5,   0.0,  0.4,  0.4,  1.0,
                         -0.5, -0.5,   0.0,  0.4,  0.4,  1.0,
                         0.5, -0.5,   0.0,  1.0,  0.4,  0.4]))

(def ^:const fsize (.-BYTES_PER_ELEMENT vertex-colors))

(defn init-vertex-buffers
  []
  (let [buf (gl/create-buffer)
        a-pos (gl/attrib-location "a_pos")
        a-color (gl/attrib-location "a_color")]
    (gl/bind-buffer gl/ARRAY_BUFFER buf)
    (gl/buffer-data gl/ARRAY_BUFFER vertex-colors gl/STATIC_DRAW)
    (gl/vertex-attrib-pointer a-pos 3 gl/FLOAT false (* fsize 6) 0)
    (gl/enable-vertex-attrib-array a-pos)
    (gl/vertex-attrib-pointer a-color 3 gl/FLOAT false (* fsize 6)
                              (* fsize 3))
    (gl/enable-vertex-attrib-array a-color)
    (gl/bind-buffer gl/ARRAY_BUFFER nil)
    9))

(defn draw
  [eye-point look-at up n u-view-matrix view-matrix]
  (mat4/makeLookAt view-matrix eye-point look-at up)
  (gl/uniform-mat4 u-model-view-matrix false model-view-matrix))

(defn handle-key-down
  [eye-point look-at up n u-view-matrix view-matrix]
  (fn [e]
    (case (.-keyCode e)
      39 (draw eye-point look-at up n u-view-matrix view-matrix)
      37 (draw eye-point look-at up n u-view-matrix view-matrix)
      nil)))

(defn -main
  []
  (webgl/root (fn [data owner]
                (gl/init-shaders webgl.look-at.vertex webgl.look-at.fragment)
                (let [n (init-vertex-buffers)
                      loc "model_view_matrix"
                      u-model-view-matrix (gl/uniform-location loc)
                      eye-point (doto (vec3/create)
                                  (vec3/setFromValues 0.20 0.25 0.25))
                      look-at (doto (vec3/create)
                                (vec3/setFromValues 0 0 0))
                      up (doto (vec3/create)
                           (vec3/setFromValues 0 1 0))
                      view-matrix (doto (mat4/create)
                                    (mat4/makeLookAt eye-point look-at up))
                      model-matrix (doto (mat4/create)
                                     (mat4/makeRotate -10 0 0 1))
                      model-view-matrix (mat4/multMat view-matrix
                                                      model-matrix
                                                      (mat4/create))]
                  (gl/clear-color 0 0 0 1)
                  
                  (gl/clear (bit-or gl/COLOR_BUFFER_BIT
                                    gl/DEPTH_BUFFER_BIT))
                  (gl/draw-arrays gl/TRIANGLES 0 n)))
              app-state
              {:target (sel1 :#app)}))
