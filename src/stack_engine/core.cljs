(ns stack-engine.core
  "Create application state with mount."
  (:require
   [cljs.nodejs :as nodejs]
   [mount.core :refer [defstate] :as mount]
   [stack-engine.keys :as keys]
   [stack-engine.resize :as resize]))

;; Import required npm & node dependencies
(def blessed (js/require "blessed"))
(def react-blessed (js/require "react-blessed"))

;; Setup mount to work in ClojureScript
(mount/in-cljc-mode)

(defstate screen
  "Blessed screen stores state like terminal size and provides methods for
  binding keys.
  https://github.com/chjj/blessed#screen-from-node"
  :start
  (doto
    (.screen blessed
             #js {:autoPadding true
                  :smartCSR true
                  :title "stack-engine"})
    resize/setup
    keys/setup))

;; Create a render function to translate hiccup into blessed components
(defonce render (.createBlessedRenderer react-blessed blessed))
