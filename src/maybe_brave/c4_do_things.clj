(ns maybe-brave.c4-do-things
  (:require [clojure.string :as str]))

;; 1. Syntax things
(+ 1 2 3)

;; If
(if true
  "abracadabra"
  "hocuspocus")

;; Do
(if true
  (do (println "Success!")
      "abracadabra")
  (do (println "Failure!")
      "hocuspocus"))

;; When
(when true
  "abracadabra")


;; def
(def failed-heros
  ["Larry Potter"
   "Doreen the Explorer"
   "The Incredible Bulk"])

(def severity :mild)

(def error-message "ARGH THIS IS REALLY BAD!!!")

;; Functions
(defn favourite-things [name & things]
  (str "Hi, "  name ", here are my favourite things: "
       (str/join ", " things)))

(favourite-things "Doreen" "gum" "shoes" "kara-te")

;; Destructuring a map
(defn show-person [{:keys [name age height]}]
  [name age height])


;; Anonymous functions
(#(+ % %) 2)
((fn [x] (* x x)) 3)
(map #(str "Hi, " %) ["Eric" "John" "Ann"])

;; Returning functions
(defn adder [n]
  #(+ n %))

(adder 3)
((adder 3) 10)
(map (adder 3) (range 10))

;; Putting it all together
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])
(defn need-matching-part?
  [part]
  (re-find #"^left" (:name part)))

(defn make-matching-part
  [part]
  (update part :name #(str/replace % #"^left-" "right-")))

(map make-matching-part asym-hobbit-body-parts)

(defn symmetrize
  [hobbit]
  (->> hobbit
       (concat (map make-matching-part hobbit))
       distinct
       (sort-by :name)))

(symmetrize asym-hobbit-body-parts)
