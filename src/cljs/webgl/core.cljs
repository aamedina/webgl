(ns webgl.core
  (:require [om.core :as om]
            [om.dom :as dom]
            [dommy.core :refer-macros [sel1]]
            [goog.events :as e]
            [goog.events.EventType :as et]
            [goog.webgl :as gl])
  (:require-macros [webgl.core :as gl]))

(defonce app-state (atom {}))

(def ^:dynamic *gl* nil)

(def frame 0)

(defn canvas
  [{:keys [setup render] :as data} owner]
  (reify
    Object
    (onResize [_ e]
      (js/console.log e))
    om/IDidMount
    (did-mount [this]
      (let [canvas (om/get-node owner)] 
        (binding [*gl* (.getContext canvas "webgl")]
          (setup data owner)
          (letfn [(render-frame []
                    (set! frame (js/requestAnimationFrame render-frame))
                    (render data owner))]
            (e/listen js/window et/RESIZE (.-onResize this) false this)
            (render-frame)))))
    om/IWillUnmount
    (will-unmount [this]
      (js/cancelAnimationFrame frame)
      (e/unlisten js/window et/RESIZE (.-onResize this) false this))
    om/IShouldUpdate
    (should-update [_ _ _] false)
    om/IRender
    (render [_]
      (dom/canvas #js {:id "canvas"
                       :width js/window.innerWidth
                       :height js/window.innerHeight}))))

(defn setup
  [data owner]
  (gl/clear-color 0.0 0.0 0.0 1.0)
  (gl/clear gl/COLOR_BUFFER_BIT))

(defn render
  [data owner]
  )

(defn -main
  []
  (swap! app-state assoc :render render :setup setup)
  (om/root canvas app-state {:target (sel1 :#app)}))
