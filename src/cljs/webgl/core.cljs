(ns webgl.core
  (:require [om.core :as om]
            [om.dom :as dom]
            [dommy.core :as d :refer-macros [sel1]]
            [goog.events :as e]
            [goog.events.EventType :as et]
            [goog.webgl :as gl]
            [goog.style :as s])
  (:require-macros [webgl.core :as gl]))

(defonce app-state (atom {}))

(defonce ^:dynamic *gl* nil)

(defprotocol IInitProgram
  (init-attribs [this])
  (init-uniforms [this])
  (init-varyings [this])
  (init-program [this]))

(defprotocol IDidMount
  (did-mount [this canvas]))

(defprotocol IWillUnmount
  (will-unmount [this canvas]))

(defprotocol IRender
  (render [this]))

(defprotocol IRenderState
  (render-state [this state]))

(extend-type default 
  IInitProgram
  (init-program [_])
  (init-attribs [_])
  (init-uniforms [_])
  (init-varyings [_])
  IDidMount
  (did-mount [_ canvas])
  IWillUnmount
  (will-unmount [_ canvas])
  IRender
  (render [_]))

(def frame 0)

(defn set-attrib!
  [owner k v]
  (let [attrib (gl/attrib-location (.-program *gl*) (name k))]
    (if (instance? js/Float32Array v)
      (case (alength v)
        1 (gl/vertex-attrib-1fv attrib v)
        2 (gl/vertex-attrib-2fv attrib v)
        3 (gl/vertex-attrib-3fv attrib v)
        4 (gl/vertex-attrib-4fv attrib v))
      (gl/vertex-attrib-1f attrib v))
    (om/set-state-nr! owner [:attributes k] v)))

(defn set-uniform!
  [owner k v]
  (let [uniform (gl/uniform-location (.-program *gl*) (name k))]
    (if (instance? js/Float32Array v)
      (case (alength v)
        1 (gl/uniform-1fv uniform v)
        2 (gl/uniform-2fv uniform v)
        3 (gl/uniform-3fv uniform v)
        4 (gl/uniform-4fv uniform v))
      (gl/uniform-1i uniform v))
    (om/set-state-nr! owner [:uniforms k] v)))

(def ^boolean resizing? false)

(defn canvas
  [{:keys [canvas] :as data} owner]
  (reify
    Object
    (onResize [_ e] (set! resizing? true))
    om/IDidMount
    (did-mount [this]
      (let [node (om/get-node owner)
            o (canvas data owner)]
        (om/set-state-nr! owner :canvas o)
        (set! *gl* (.getContext node "webgl"))
        (init-program o)
        (doseq [[k v] (init-attribs o)]
          (set-attrib! owner k v))
        (doseq [[k v] (init-uniforms o)]
          (set-uniform! owner k v))
        (did-mount o node)
        (letfn [(render-frame []
                  (set! frame (js/requestAnimationFrame render-frame))
                  
                  (when resizing?
                    (set! resizing? false)
                    (.viewport *gl* 0 0
                               (set! (.-width node) js/window.innerWidth)
                               (set! (.-height node) js/window.innerHeight)))
                  (render o))]
          (e/listen js/window et/RESIZE (.-onResize this) false this)
          (render-frame))))
    om/IWillUnmount
    (will-unmount [this]
      (js/cancelAnimationFrame frame)
      (will-unmount (om/get-state owner :canvas) (om/get-node owner))
      (e/unlisten js/window et/RESIZE (.-onResize this) false this))
    om/IShouldUpdate
    (should-update [_ _ _] false)
    om/IRender
    (render [_]
      (dom/canvas #js {:id "canvas"
                       :width js/window.innerWidth
                       :height js/window.innerHeight}))))

(defn my-canvas
  [data owner]
  (reify
    Object   
    (onClick [this e]
      (let [canvas (om/get-node owner)
            point [(/ (- (.-clientX e) (/ (.-width canvas) 2))
                      (/ (.-width canvas) 2))
                   (/ (- (/ (.-height canvas) 2) (.-clientY e))
                      (/ (.-height canvas) 2))]
            points (conj (or (om/get-state owner :points) #{}) point)]
        (om/set-state! owner :points points)
        (gl/clear gl/COLOR_BUFFER_BIT)
        (doseq [[x y] points]
          (js/console.log x y)
          (set-attrib! owner :pos (gl/vec4 x y 0.0 1.0))
          (gl/draw-arrays gl/POINTS 0 1))))
    IInitProgram
    (init-program [_]
      (gl/init-shaders webgl.hello-vertex webgl.hello-fragment))
    (init-attribs [_]
      {:pos (gl/vec4 0.0 0.0 0.0 1.0)
       :size 10.0})
    (init-uniforms [_]
      {:color (gl/vec4 1.0 0.0 0.0 1.0)})
    IDidMount
    (did-mount [this canvas]
      (e/listen canvas et/MOUSEDOWN (.-onClick this) false this)
      (gl/clear-color 0.0 0.0 0.0 1.0)
      (gl/clear gl/COLOR_BUFFER_BIT))
    IWillUnmount
    (will-unmount [this canvas]
      (e/unlisten canvas et/MOUSEDOWN (.-onClick this) false this))
    IRender
    (render [_])))

(defn -main
  []
  (swap! app-state assoc :canvas my-canvas)
  (om/root canvas app-state {:target (sel1 :#app)}))
