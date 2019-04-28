(ns store.views.landing
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]))

(defn landing
  []
   [:main {:role "main"}
    [:section.jumbotron.text-center
     [:div.container
      [:h1.jumbotron-heading "Album example"]
      [:p.lead.text-muted "Something short and leading about the collection below—its contents, the creator, etc. Make it short and sweet, but not too short so folks don’t simply skip over it entirely."]
      [:p
       [:a {:href "#" :class "btn btn-primary my-2" :on-click #(dispatch [:set-showing (keyword "signup")])} "Sign up now"]
       [:a {:href "#" :class "btn btn-secondary my-2" :on-click #(dispatch [:set-showing (keyword "login")])} "Login"]]]]])