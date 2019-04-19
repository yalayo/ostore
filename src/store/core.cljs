(ns store.core
  (:require [reagent.core :as r]))

(defn checkout-component []
  [:div.container
   [:div.py-5.text-center
    [:img.d-block.mx-auto.mb-4 {:src "" :width "72" :height "72"} ]
    [:h2 "Checkout form"]
    [:p.lead "Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it."]
    ]

   [:div.row
    [:div.col-md-4.order-md-2.mb-4
     [:h4.d-flex.justify-content-between.align-items-center.mb-3
      [:span.text-muted "Your cart"]
      [:span.badge.badge-secondary.badge-pill "3"]]
     [:ul.list-group.mb-3
      [:li.list-group-item.d-flex.justify-content-between.lh-condensed
       [:div
        [:h6.my-0 "Product name"]
        [:small.text-muted "Brief description"]]
       [:span.text-muted "$12"]]
      [:li.list-group-item.d-flex.justify-content-between.lh-condensed
       [:div
        [:h6.my-0 "Second product"]
        [:small.text-muted "Brief description"]]
       [:span.text-muted "$8"]]
      [:li.list-group-item.d-flex.justify-content-between.lh-condensed
       [:div
        [:h6.my-0 "Third product"]
        [:small.text-muted "Brief description"]]
       [:span.text-muted "$5"]]
      [:li.list-group-item.d-flex.justify-content-between.bg-light
       [:div.text-success
        [:h6.my-0 "Promo code"]
        [:small "EXAMPLECODE"]]
       [:span.text-muted "-$5"]]
      [:li.list-group-item.d-flex.justify-content-between
       [:span "Total (USD)"]
       [:strong "$20"]]]

     [:form.card.p-2
      [:div.input-group
       [:input.form-control {:type "text" :placeholder "Promo code"}]
       [:div.input-group-append
        [:button.btn.btn-secondary {:type "submit"} "Redeem"]]]]]
    [:div.col-md-8.order-md-1
     [:h4.mb-3 "Billing address"]
     [:form.needs-validation
      [:div.row
       [:div.col-md-6.mb-3
        [:label {:for "firstName"} "First name"]
        [:input.form-control {:type "text" :id "firstName" :placeholder "" :value ""}]]
       [:div.col-md-6.mb-3
        [:label {:for "lastName"} "Last name"]
        [:input.form-control {:type "text" :id "lastName" :placeholder "" :value ""}]]]
      [:div.mb-3
       [:label {:for "address"}]
       [:input.form-control {:type "text" :id "address" :placeholder "1234 Main St"}]]
      [:div.mb-3
       [:label {:for "address2"}
        [:span.text-muted "(Optional)"]]
       [:input.form-control {:type "text" :id "address2" :placeholder "Apartment or suite"}]]
      [:div.row
       [:div.col-md-4.mb-3
        [:label {:for "country"} "Country"]
        [:select#country.custom-select.d-block.w-100
         [:option {:value ""} "Choose..."]
         [:option "United States"]]]
       [:div.col-md-4.mb-3
        [:label {:for "state"} "State"]
        [:select#state.custom-select.d-block.w-100
         [:option {:value ""} "Choose..."]
         [:option "California"]]]
       [:div.col-md-4.mb-3
        [:label {:for "zip"} "Zip"]
        [:input#zip.form-control {:type "text" :placeholder ""}]]]
      [:hr.mb-4]
      [:div.custom-control.custom-checkbox
       [:input.custom-control-input {:type "checkbox" :id "same-address"}]
       [:label.custom-control-label {:for "same-address"} "Shipping address is the same as my billing address"]]
      [:div.custom-control.custom-checkbox
       [:input.custom-control-input {:type "checkbox" :id "save-info"}]
       [:label.custom-control-label {:for "save-info"} "Save this information for next time"]]
      [:hr.mb-4]
      [:h4.mb-3 "Payment"]
      [:hr.mb-4]
      [:button.btn.btn-primary.btn-lg.btn-block {:type "submit"} "2Checkout"]]]]

   [:br]
   [:fotter.my-5.pt-5.text-muted.text-center.text-small
    [:p.mb-1 "2019 Onmycrowd"]
    [:ul.list-inline
     [:li.list-inline-item
      [:a {:href "#"} "Privacy"]]
     [:li.list-inline-item
      [:a {:href "#"} "Refund policy"]]
     [:li.list-inline-item
      [:a {:href "#"} "Support"]]]]])

(defn ^:export run []
  (r/render [checkout-component]
            (js/document.getElementById "app")))

(run)