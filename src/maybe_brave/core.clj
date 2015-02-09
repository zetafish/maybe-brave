(ns maybe-brave.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; nil, true, false,Truthiness, Equality
(nil? 1) ; => false
(nil? nil) ; => true
(= 1 1)    ; => true
(= 1 2)    ; => false


;; 3.5 Returning functions
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}])

(defn needs-matching-part?
  [part]
  (re-find #"^left-" (:name part)))

(defn make-matching-part
  [part]
  {:name (str/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps which have a :name and :size"
  [asym-body-parts]
  (flatten
   (map (fn [part]
          (if (needs-matching-part? part)
            [part (make-matching-part part)]
            part))
        asym-body-parts)))

(symmetrize-body-parts asym-hobbit-body-parts)
