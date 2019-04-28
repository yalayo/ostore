(ns store.views.home
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]
            [store.views.landing :as lp]))

(defmulti render-view (fn [v & args] v))

(defmethod render-view :home
           [_ s]
           (lp/landing))

(defn current
  []
  (fn []
    (render-view @(subscribe [:showing]))))