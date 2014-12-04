(ns webgl.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.webgl :as gl])
  (:require-macros [webgl.core :as gl]))

(defonce ^:dynamic *gl* nil)

(defonce frame 0)

(defn root
  [f value options]
  (om/root (fn [data owner]
             (reify
               om/IDidMount
               (did-mount [this]
                 (let [node (om/get-node owner)
                       render (fn render []
                                (set! frame (js/requestAnimationFrame render))
                                (f data owner))]
                   (set! *gl* (.getContext node "webgl"))
                   (render)))               
               om/IWillUnmount
               (will-unmount [this]
                 (set! *gl* nil)
                 (js/cancelAnimationFrame frame))
               om/IRenderProps
               (render-props [_ props state]                 
                 (dom/canvas #js {:width (:width props)
                                  :height (:height props)}))))
           value
           options))
