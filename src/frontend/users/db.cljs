(ns users.db
  (:require [reagent.core :as reagent :refer [atom]]
            ))

(defonce app-state
  (atom
   {
     :cognito {:userPoolId "us-east-XXXXXXXXXX"
               :userPoolClientID "XXXXXXXXXXXXXXXXXXXXXXXXX"
               :region "us-east-1"}
     :cognito-user {}
     }))