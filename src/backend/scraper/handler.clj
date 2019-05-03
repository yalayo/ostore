(ns server.handler
    (:require [compojure.api.sweet :refer :all]
              [ring.util.http-response :refer :all]
              [schema.core :as s]
              [backend.scraper.core :as scraper]))

(require '[ring.adapter.jetty :refer [run-jetty]])
(require '[ring.middleware.defaults :refer [wrap-defaults site-defaults]])

(s/defschema Account
  {
    :level s/Str
    :code s/Str
    :name s/Str
    })

(def app
  (api
   {:swagger
    {:ui "/sw"
     :spec "/swagger.json"
     :data {:info {:title "Debiteer"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]}}}

   (context "/api" []
            :tags ["api"]

            (GET "/opened" []
                 :return s/Bool
                 :summary "Nothing specific for now"
                 (ok (scraper/check-if-available))))))

(run-jetty
 (wrap-defaults app site-defaults)
 {:host "0.0.0.0" :port 3000})