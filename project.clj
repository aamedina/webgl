(defproject webgl "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha4"]
                 [org.clojure/clojurescript "0.0-2390"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/core.rrb-vector	"0.0.11"]
                 [org.clojure/data.avl "0.0.12"]
                 [org.clojure/tools.analyzer.jvm "0.6.4"]
                 [cljs-http "0.1.20"]
                 [prismatic/dommy "1.0.0"]
                 [clojure-gl "0.1.0-SNAPSHOT"]
                 [om "0.8.0-alpha2"]]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :source-paths ["src" "src/cljs"]
  :profiles {:dev {:dependencies [[figwheel "0.1.5-SNAPSHOT"]
                                  [weasel "0.4.2"]]
                   :plugins [[lein-figwheel "0.1.5-SNAPSHOT"]
                             [com.cemerick/austin "0.1.5"]]
                   :source-paths ["dev"]
                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]}}}
  :resource-paths ["resources" "src/cgl"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs" "dev"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none
                                   :source-map true}}
                       {:id "adv"
                        :source-paths ["src/cljs"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :optimizations :advanced
                                   :pretty-print false
                                   :preamble ["react/react_with_addons.min.js"]
                                   :externs ["react/externs/react.js"]}}]})
