(ns store.core
  (:require [reagent.core :as r]))


(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn checkout-component []
  [:div.container
   [:div.py-5.text-center
    [:img.d-block.mx-auto.mb-4 {:src "" :width "72" :height "72"} ]
    [:h2 "Checkout form"]
    [:p.lead "Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it."]
    ]])

(defn ^:export run []
  (r/render [checkout-component]
            (js/document.getElementById "app")))

(run)