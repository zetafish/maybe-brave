(ns maybe-brave.macros
  (:require [clojure.java.io :as io]))

;;; See: http://www.braveclojure.com/writing-macros/

;; vanilla Clojure
(defn read-resource
  [path]
  (read-string (slurp (io/resource path))))

(defn read-resource
  [path]
  (-> path io/resource slurp read-string))

(macroexpand '(when boolean-expr
                exp1
                exp2
                exp3))

;;; 2 Anatomy of a macro
(defmacro postfix
  [expr]
  (conj (butlast expr) (last expr)))

(postfix (1 2 +))

;; destructurin of macro arguments works just
(defmacro code-critic
  [{:keys [good bad]}]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))

(code-critic {:good (+ 1 1) :bad (1 + 1)})
(= (quote +) '+)
(class 'i-am-brian)

(defmacro when
  [test & body]
  (list 'if test (cons 'do body)))

(defmacro unless
  [test & branches]
  (conj (reverse branches) test 'if))

(defmacro code-critic
  [{:keys [good bad]}]
  `(do (println "This is so bad:" (quote ~bad))
       (println "This is so good:" (quote ~good))))

(code-critic {:good (+ 1 1) :bad (1 + 1)})

;; more clean
(defn criticize
  [how code]
  `(println ~how (quote ~code)))

(defmacro code-critic
  [{:keys [good bad]}]
  `(do ~@(map #(apply criticize %)
             [["Cursed you:" bad]
              ["Praise the lord:" good]])))

(code-critic {:good (+ 1 2) :bad (1 + 2)})

(clojure.pprint/pprint (macroexpand '(code-critic {:good (+ 1 2) :bad (1 + 2)})))

;;; 6. Variable Capture
(def message "Good Job!")
(defmacro nasty
  [& stuff]
  (concat (list 'let ['message "Oh big deal"]
                stuff)))

(defmacro no-nasty
  [& stuff]
  (let [mm (gensym 'message)]
    `(let [~mm "Oh big deal"]
       ~@stuff
       (println "I still need to say:" ~mm))))

(no-nasty "I feel cool" message)

(nasty (println "I feel cool") message)

;; 7 Final Thought
(def shipping-details
  {:name "Mark Simmons"
   :address "1234 lane"
   :city ""
   :email "??"})

(def shipping-details-validations
  {:name ["Please enter a name" not-empty]
   :city ["Please enter a city" not-empty]
   :email ["Please enter an email address" not-empty
           "Bad email address" (or #(empty? %)
                                   #(re-seq #"@" %))]})

(defn error-messages-for
  [to-validate msg-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 msg-validator-pairs))))

(error-messages-for "" ["Please enter a city" not-empty])

(error-messages-for "shine bright like a diamond"
                    ["Please enter a postal code" not-empty
                     "Please enter a postal code that looks like a US postal code"
                     #(or (empty? %)
                          (not (re-seq #"[^0-9-]" %)))])

(defn validate [data rules]
  "Returns a map with a vec of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[field ]]))))

(validate shipping-details shipping-details-validations)
