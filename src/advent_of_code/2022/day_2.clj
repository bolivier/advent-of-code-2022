(ns advent-of-code.2022.day-2
  (:require [clojure.string :as str]))

(def lookup
  {"A" :rock
   "B" :paper
   "C" :scissors

   "X" :rock
   "Y" :paper
   "Z" :scissors})

(def file-contents "A Y
B X
C Z")

(def defeated-by {:rock     :scissors
                  :paper    :rock
                  :scissors :paper})

(defn win? [them me]
  (= them (defeated-by me)))

(defn tie? [them me]
  (= them me))

(def win-bonus 6)
(def tie-bonus 3)
(def loss-bonus 0)

(def shape-score {:rock     1
                  :paper    2
                  :scissors 3})

(defmulti score-round
  (fn [them me]
    (cond
      (win? them me)
      :win

      (tie? them me)
      :tie

      :else
      :loss)))

(defmethod score-round :win [them me]
  (+ (shape-score me) win-bonus))

(defmethod score-round :tie [them me]
  (+ (shape-score me) tie-bonus))

(defmethod score-round :loss [them me]
  (shape-score me))

(defn solution []
  (let [file-contents (slurp "inputs/2022/day-2")]
   (reduce + (map
              #(apply score-round %)

              (map #(map lookup %) (map #(str/split % #" ")
                                        (str/split-lines file-contents)))))))

(comment (solution );; => 12679
)
