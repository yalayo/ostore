(ns store.core
  (:require-macros [secretary.core :refer [defroute]]
                   [cljs.core.async.macros :refer [go go-loop]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as r]
            [clojure.core.async :as async :refer [put! chan <! >!]]
            [store.checkout :as co]))

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

(defn home []
 [:div [:h1 "Home Page"]
  [:a {:href "#/about"} "About page"]
  [:br]
  [:a {:href "#/checkout"} "Checkout page"]
  [:br]
  [:a {:href "#/upaccounts"} "Upload accounts"]])

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
           [home])
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