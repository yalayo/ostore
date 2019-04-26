(ns store.core
  (:require-macros [secretary.core :refer [defroute]]
                   [cljs.core.async.macros :refer [go go-loop]])
  (:import [goog History]
           [goog.history EventType])
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as reagent]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [clojure.core.async :as async :refer [put! chan <! >!]]
            [store.events] ;; These two are only required to make the compiler
            [store.subs]   ;; load them (see docs/Basic-App-Structure.md)
            [store.views.home]
            [devtools.core :as devtools]
            [store.checkout :as co]
            [store.home :as h]))

;; -- Debugging aids ----------------------------------------------------------
(devtools/install!)       ;; we love https://github.com/binaryage/cljs-devtools
(enable-console-print!)   ;; so that println writes to `console.log`

;; Put an initial value into app-db.
;; The event handler for `:initialise-db` can be found in `events.cljs`
;; Using the sync version of dispatch means that value is in
;; place before we go onto the next step.
(dispatch-sync [:initialise-db])

;; -- Routes and History ------------------------------------------------------
;; Although we use the secretary library below, that's mostly a historical
;; accident. You might also consider using:
;;   - https://github.com/DomKM/silk
;;   - https://github.com/juxt/bidi
;; We don't have a strong opinion.
;;
(defroute "/" [] (dispatch [:set-showing :all]))
(defroute "/:filter" [filter] (dispatch [:set-showing (keyword filter)]))

(def history
  (doto (History.)
        (events/listen EventType.NAVIGATE
                       (fn [event] (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -- Entry Point -------------------------------------------------------------
;; Within ../../resources/public/index.html you'll see this code
;;    window.onload = function () {
;;      todomvc.core.main();
;;    }
;; So this is the entry function that kicks off the app once the HTML is loaded.
;;
(defn ^:export main
  []
  ;; Render the UI into the HTML's <div id="app" /> element
  ;; The view function `todomvc.views/todo-app` is the
  ;; root view for the entire UI.
  (reagent/render [store.views.home/home]
                  (js/document.getElementById "app")))