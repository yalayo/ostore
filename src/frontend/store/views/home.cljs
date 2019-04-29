(ns store.views.home
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]
            [store.views.landing :as lp]
            [users.views.signup :as su]))

(defmulti render-view (fn [v & args] v))

(defmethod render-view :home
           [_ s]
           (lp/landing))

(defmethod render-view :signup
           [_ s]
           (su/signup))

(defn current
  []
  (fn []
    (render-view @(subscribe [:showing]))))