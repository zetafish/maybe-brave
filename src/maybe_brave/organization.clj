(ns maybe-brave.organization
  (:refer-clojure :exclude [min max])
  (:require [clojure.string :as str]))

(ns-name *ns*)

;; 2. Storing objects with def
;;
(def great-books ["East of Eden" "Through the Looking Glass"])
great-books

;; Return map of interned Vars
(ns-interns *ns*)

;; Get a specific Var
(get (ns-interns *ns*) 'great-books)

(deref #'great-books)

;;; 3. Creating and Switching to NAmespaces
(create-ns 'cheese.taxonomy)
(ns-name (create-ns 'cheese.taxonomy))
(in-ns 'cheese.analysis)


;;; 4. Refs and Alias
;; NB. should do this in the REPL:
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong"])

;; private functions
(defn- private-function
  "Just an example function that does nothing"
  [])

(defn comparator-over-maps
  [cmp keys]
  (fn [maps]
    (reduce (fn [result current-map]
              (reduce merge
                      (map (fn [key]
                             {key (cmp (key result))}
                             keys)))
              maps))))

(def min (comparator-over-maps clojure.core/min [:lat :lng]))
(def max (comparator-over-maps clojure.core/max [:lat :lng]))

(defn translate-to-00
  [locs]
  (let [mincoords (min locs)]
    (map #(merge-with - % mincoords) locs)))

(defn scale
  [w h l]
  (let [maxcoords (max l)
        ratio {:lat (/ h (:lat maxcoords))
               :lng (/ w (:lng maxcoords))}]
    (map #(merge-with * % ratio) l)))

(defn latlng->point
  [{:keys [lng lat]}]
  (str lng "," lat))

(defn points
  [l]
  (str/join " " (map latlng->point l)))

(defn line
  [points]
  (str "<polyline points=\"" points "\" />"))

(defn transform
  [w h l]
  (->> l
       translate-to-00
       (scale w h)))

(defn xml
  [w h l]
  (str "<svg height=\"" h "\" width=\"" w "\">"
       ;; these two <g> tags flip coordinate system
       "<g transform=\"translate(0," h ")\">"
       "<g transform=\"scale(1,-1)\">"
       (-> (transform w h l)
           points
           line)
       "</g></g>"
       "</svg>"))
