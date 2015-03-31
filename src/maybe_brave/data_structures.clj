(ns maybe-brave.ch2-data-structures)

;;; Do-Things, Ch 2

;; 2.1 Thruthiness
(nil? 1)

(nil? nil)

(= 1 1)

(= 1 2)

;; 2.2 Numbers
93
1.2
1/5

;; 2.3 Strings
"Lord Voldemort"
"\"He who must not be named\""
"\"Great cow of Moscol!\" - Hermes Conrad"

(def n "Chipucabra")
(str "\"Ugglg\" - " n)

;; 2.4 Maps

;; An empty map
{}

;; Simple map example
{:a 1
 :b "boring example"
 :c []}

{"string-key" +}

;; Nested maps
{:name {:first "John" :middle "X" :last "Doe"}}

;; Lookup items in a map
(get {:a 0 :b 1} :b)
(get {:a 0 :b {:c "yo ho"}} :b)

;; nil if not found
(get {:a 0 :b 1} :c)

;; Return a default value
(get {:a 0 :b 1} :c "UNICORNS")

;; Look in nested maps
(get-in {:a 0 :b {:c "yo ho"}} [:b :c])

;; Use map as a function - but that is not idiomatic
({:a 1 :b 2} :b)

;; 2.5 Keywords
:a
:repelsteeltje
:23
:_?

;; Keywords can be used as functions to lookup values in a data
;; structure. For example:
(:a {:a 1 :b 2 :c 3})

;; Works also with a default
(:d {:a 1 :b 2 :c 3} "CATSNIP")

;; There is also a function called `hash-map` to create a map.
(hash-map :a 1 :b 2)

;; 2.6 Vectors
[3 2 1]

;; Return the ith element of a vector
(get [3 2 1] 0)

;; Vector can be treated as a function
([3 2 1] 0)

;; There is also a `vector` function
(vector 1 2 3)

;; Elements are added to the end of a vector
(conj [1 2 3] 4)

;; 2.7 Lists
'(1 2 3 4)

(get '(1 2 3 4) 0)
