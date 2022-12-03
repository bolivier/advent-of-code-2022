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

(def defeated-by {:rock :scissors
                  :paper     :rock
                  :scissors    :paper})
(def defeats (clojure.set/map-invert defeated-by))

(defn win? [them me]
  (= them (defeated-by me)))

(defn tie? [them me]
  (= them me))

(def win-bonus 6)
(def tie-bonus 3)

(def shape-score {:rock     1
                  :paper    2
                  :scissors 3})

(defprotocol Scoreable
  (score [round] "Score a round of rock, paper, scissors"))

(defrecord ShapeRound [their-shape my-shape]
  Scoreable
  (score [{:keys [their-shape my-shape]}]
    [their-shape my-shape]
  (cond
    (win? their-shape my-shape)
    (+ (shape-score my-shape) win-bonus)

    (tie? their-shape my-shape)
    (+ (shape-score my-shape) tie-bonus)

    :else
    (shape-score my-shape))))

(def lookup-outcome {"X" :loss
                     "Y" :tie
                     "Z" :win})

(defrecord WinLossRound [their-shape outcome]
  Scoreable
  (score [{:keys [their-shape outcome]}]
    (case outcome
      :win
      (score (->ShapeRound their-shape (defeats their-shape)))
      :loss
      (score (->ShapeRound their-shape (defeated-by their-shape)))
      :tie
      (score (->ShapeRound their-shape their-shape)))))

(defn- parse-file-contents [file-contents]
  (map #(str/split % #" ")
       (str/split-lines file-contents)))

(defn read-input []
  (let [file-contents (slurp "inputs/2022/day-2")]
    (parse-file-contents file-contents)))

(comment (def input (read-input)))

(defn solution []
  (let [input (read-input)
        create-shape-round (fn [[them me]]
                             (->ShapeRound (lookup them) (lookup me)))]
    (->> input
         (map create-shape-round)
         (map score)
         (reduce +))))

(comment (solution );; => 12679
)

(defn solution2 []
  (let [input (read-input)
        create-win-loss-round
        (fn [[them outcome]]
          (->WinLossRound (lookup them) (lookup-outcome outcome)))]
    (->> input
         (map create-win-loss-round)
         (map score)
         (reduce +))))
