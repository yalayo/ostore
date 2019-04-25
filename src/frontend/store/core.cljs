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
            [store.views]
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
  (reagent/render [store.views/store-app]
                  (.getElementById js/document "app")))



(def app-state (r/atom {}))


(defn hook-browser-navigation! []
 (doto (Html5History.)
       (events/listen
        EventType/NAVIGATE
        (fn [event]
         (secretary/dispatch! (.-token event))))
       (.setEnabled true)))

(defn app-routes []
 (secretary/set-config! :prefix "#")

 (defroute "/" []
           (swap! app-state assoc :page :home))

 (defroute "/about" []
           (swap! app-state assoc :page :about))

 (defroute "/checkout" []
           (swap! app-state assoc :page :checkout))

 (defroute "/upaccounts" []
           (swap! app-state assoc :page :upaccounts))

 (hook-browser-navigation!))

(defmulti current-page #(@app-state :page))

(defn about []
 [:div [:h1 "About Page"]
  [:a {:href "#/"} "home page"]])

(def first-file
 (map (fn [e]
       (let [target (.-currentTarget e)
             file (-> target .-files (aget 0))]
        (set! (.-value target) "")
        file))))

(def upload-reqs (chan 1 first-file))

(defn put-upload [e]
 (put! upload-reqs e))

(defn upload-btn [file-name]
 [:span.upload-label
  [:label
   [:input.hidden-xs-up
    {:type "file" :accept ".csv" :on-change put-upload}]
   [:i.fa.fa-upload.fa-lg]
   (or file-name "click here to upload and render csv...")]
  (when file-name
        [:i.fa.fa-times {:on-click #(reset! app-state {})}])])



(def extract-result
 (map #(-> % .-target .-result js->clj)))


(def file-reads (chan 1 extract-result))



(go-loop []
         (let [reader (js/FileReader.)
               file (<! upload-reqs)]
          (swap! app-state assoc :file-name (.-name file))
          (set! (.-onload reader) #(put! file-reads %))
          (.readAsText reader file)
          (recur)))

(go-loop []
         (swap! app-state assoc :data (<! file-reads))
         (recur))


(defn upaccounts []
 [:span.upload-label
  [:label
   [:input.hidden-xs-up
    {:type "file" :accept ".csv" :on-change put-upload}]
   [:i.fa.fa-upload.fa-lg]
   (or file-name "click here to upload and render csv...")]
  (when file-name
        [:i.fa.fa-times {:on-click #(reset! app-state {})}])])



(defmethod current-page :home []
           [h/home])
(defmethod current-page :about []
           [about])
(defmethod current-page :checkout []
           [co/checkout])

(defmethod current-page :upaccounts []
           [upaccounts])

(defmethod current-page :default []
           [:div ])

(defn ^:export run []
 (app-routes)
 (r/render [current-page]
           (js/document.getElementById "app")))

(run)