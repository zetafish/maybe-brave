(ns maybe-brave.concurrency)

;;; See: http://www.braveclojure.com/concurrency/

;; 4.1 Futures
(do
  (let [r (future (Thread/sleep 4000)
                  (println "I will print after 4 seconds")
                  (+ 1 2))]
    (println "deref: " (deref r))
    (println "@:" @r))
  (println "I will print immediately"))

;; limit the time to wait
(deref (future (Thread/sleep 1000) 0) 10 :peanut)

;; examine a future
(let [f (future)]
  (realized? f))

;; 4.2
(web-api/get :dwarven-beard-waxes)

;; 4.3
(def jackson-5-delay
  (delay (let [m "Just call my name and I'll be there"]
           (println "First deref:" m)
           m)))

(force jackson-5-delay)

;; delayed task is executed only once during the first force
(defn email-user [loc])
(defn upload-doc [doc])
(def pictures ["a" "b" "c"])
(let [notify (delay (email-user "brandon@lotr.com"))]
  (doseq [p pictures]
    (future (upload-doc p)
            (force notify))))  ; only notified once

;; 4.4 Promises
(def my-promise (promise))
(deliver my-promise (+ 1 2))
@my-promise

;; 4.5 queues
(defn append-to-file
  [fn s]
  (spit fn s :append true))

(defn format-quote
  [quote]
  (str "=== BEGIN ===\n" quote "=== END ===\n\n"))

(defn snag-quotes
  [n fn]
  (dotimes [_ n]
    (->> (slurp "http://www.iheartquotes.com/api/v1/random")
         format-quote
         (append-to-file fn)
         (future))))

(snag-quotes 2 "quotes.txt")
