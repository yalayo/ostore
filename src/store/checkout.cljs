(ns store.checkout)

(defn checkout-component []
  [:div.container
   [:div.py-5.text-center
    [:img.d-block.mx-auto.mb-4 :src "resources/imgs/bootstrap-solid.svg" :width "72" :height "72"]
    [:h2 "Checkout form"]
    [:p.lead "Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it."]
    ]])

