(ns maybe-brave.read-eval)

;;; http://www.braveclojure.com/read-and-eval/

(read-string "()")
(read-string "[1 2]")

;;; 3. Reader macros
(#(+ 1 %) 3)
(read-string "#(+ 1 %)")
(read-string "'(a b c)")
(read-string "@var")
(read-string ";asdf\n1")

;;; 4. Evaluation
(def var (atom [4 3 2]))
(eval (read-string "@var"))
(eval "t")
(eval (read-string "true"))
(eval (read-string ":asdf"))

(eval
 (let [infix (read-string "(1 + 1)")]
   (list (second infix) (first infix) (last infix))))

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))
(ignore-last-operand (+ 1 2 (println "look at me")))

(macroexpand '(ignore-last-operand (+ 1 2 10)))

(defn read-resource
  [path]
  (read-string (slurp (clojure.java.io/resource path))))

(defn read-resource
  [path]
  (-> path
      clojure.java.io/resource
      slurp
      read-string))

(defmacro backwards
  [form]
  (reverse form))

(backwards ("something" "do" "I" str))
