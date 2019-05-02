(ns backend.scraper.core
    (:import (org.jsoup Jsoup)
             (org.jsoup.select Elements)
             (org.jsoup.nodes Element)))

(defn get-webpage
  [url]
    (let [page (.get (Jsoup/connect url))]
      page))

(defn get-elems [page tag]
  (.select page tag))

(defn show-data []
  (count (get-elems (get-webpage "http://visas.migracion.gob.pa/SIVA/verif_citas/") "img")))