(ns backend.scraper.core
    (:import (org.jsoup Jsoup)
             (org.jsoup.select Elements)
             (org.jsoup.nodes Element)))

(defn get-webpage
  [url]
    (let [page (.get (Jsoup/connect url))]
      page))

(defn get-elems
  [page tag]
  (.select page tag))

(defn check-if-available
  []
  (let [imgs (count (get-elems (get-webpage "http://visas.migracion.gob.pa/SIVA/verif_citas/") "img"))]
    (if (> imgs 2)
      (let [links (count (get-elems (get-webpage "http://visas.migracion.gob.pa/SIVA/verif_citas/") "a"))]
        (> links 1))
      (> imgs 2))))