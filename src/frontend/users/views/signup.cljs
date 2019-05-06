(ns users.views.signup
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [users.events :as auth-events]))

(defn validate-view [form-vals]
  (b/validate form-vals
              :email [v/required v/email]
              :password v/required
              :confirm-password [v/required [(fn [cp]
                                               (= cp (:password form-vals)))
                                             :message
                                             "Password and Confirm Password must match"]]))

(defn signup
  []
  (let [email-cursor (subscribe [:email])
        password-cursor (subscribe [:password])
        confirm-password-cursor (subscribe [:confirm-password])
        flash-cursor (subscribe [:flash])
        touched (atom {:email false :password false :confirm-password false})
        has-verr (fn [vs field]
                   (and
                    (contains? vs field)
                    (field @touched)))
        fields (subscribe)]
    (fn []
      (let [verrs (first (validate-view @fields))]
        [:div.users
         (into [:div]
               (map
                (fn [v] [:div.alert.alert-danger
                         {:class (name (:type v))}
                         (:message v)])
                @flash-cursor))
         [:form.form-signin
          [:div.text-center.mb-4
           [:img.d-block.mx-auto.mb-4 {:src "" :width "72" :height "72"} ]]
          [:div.form-label-group {:class (when (has-verr verrs :email) "has-error")}
           [:input {:type "email" :id "inputEmail" :class "form-control" :placeholder "Email address" :value @email-cursor
                    :on-key-press
                    (auth-events/on-enter-fn auth-events/register-new-user)
                    :on-change
                    (comp
                     (fn [_] (swap! touched assoc :email true))
                     (auth-events/swap-on-value-changed! email-cursor))}]
           [:label {:for "inputEmail"} "Email address"]
           (when (has-verr verrs :email)
                 [:small.text-help (:email verrs)])]

          [:div.form-label-group {:class (when (has-verr verrs :password) "has-error")}
           [:input {:type "password" :id "inputPassword" :class "form-control" :placeholder "Password" :value @password-cursor
                    :on-key-press
                    (auth-events/on-enter-fn auth-events/register-new-user)
                    :on-change
                    (comp
                     (fn [_] (swap! touched assoc :password true))
                     (auth-events/swap-on-value-changed! password-cursor))}]
           [:label {:for "inputPassword"} "Password"]
           (when (has-verr verrs :password)
                 [:small.text-help (:password verrs)])]

          [:div.form-label-group {:class (when (has-verr verrs :confirm-password) "has-error")}
           [:input {:type "password" :id "inputPasswordConf" :class "form-control" :placeholder "Password confirmation" :value @confirm-password-cursor
                    :on-key-press
                    (auth-events/on-enter-fn auth-events/register-new-user)
                    :on-change
                    (comp
                     (fn [_] (swap! touched assoc :confirm-password true))
                     (auth-events/swap-on-value-changed! confirm-password-cursor))}]
           [:label {:for "inputPasswordConf"} "Password confirmation"]
           (when (has-verr verrs :confirm-password)
                 [:small.text-help (:confirm-password verrs)])]

          [:div.checkbox.mb-3
           [:label
            [:input {:type "checkbox"}] "Remember me"]]
          [:button.btn.btn-lg.btn-primary.btn-block "Sign up" {:on-click #(auth-events/register-new-user)
                                                               :class (when-not (empty? verrs)
                                                                                "disabled")}]]]))))