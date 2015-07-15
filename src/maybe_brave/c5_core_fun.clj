(ns maybe-brave.c5-core-fun)

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Hamsters" "Dogs"])
(map titleize '("Hamsters" "Dogs"))

(defn label-kv [[k v]]
  (str "key=" k ", val=" v))

(map label-kv {:a 1 :b 2 :c 3})

(into {}
      (map (fn [[k v]] [k (inc v)])
           {:max 30 :min 10}))

;; Abstractions and Implementations
