(ns store.views.signup
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]))

(defn signup
  []
    [:div.users
     [:form.form-signin
      [:div.text-center.mb-4
       [:img.d-block.mx-auto.mb-4 {:src "" :width "72" :height "72"} ]]
      [:div.form-label-group
       [:input {:type "email" :id "inputEmail" :class "form-control" :placeholder "Email address"}]
       [:label {:for "inputEmail"} "Email address"]]
      [:div.form-label-group
       [:input {:type "password" :id "inputPassword" :class "form-control" :placeholder "Password"}]
       [:label {:for "inputPassword"} "Password"]]
      [:div.form-label-group
       [:input {:type "password" :id "inputPasswordConf" :class "form-control" :placeholder "Password confirmation"}]
       [:label {:for "inputPasswordConf"} "Password confirmation"]]
      [:div.checkbox.mb-3
       [:label
        [:input {:type "checkbox"}] "Remember me"]]
      [:button.btn.btn-lg.btn-primary.btn-block "Sign up"]]])