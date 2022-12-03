(ns advent-of-code.2022.day-2
  (:require [clojure.string :as str]
            [clojure.set :refer [map-invert]]))

(def lookup
  {"A" :rock
   "B" :paper
   "C" :scissors

   "X" :rock
   "Y" :paper
   "Z" :scissors})
(def lookup-outcome
  {"X" :loss
   "Y" :tie
   "Z" :win})
(def score-shape
  {:rock     1
   :paper    2
   :scissors 3})
(def win-bonus 6)
(def tie-bonus 3)
(def loss-bonus 0)

(def defeated-by {:rock :scissors
                  :paper     :rock
                  :scissors    :paper})
(def defeats (map-invert defeated-by))

(defn win? [them me]
  (= them (defeated-by me)))

(defn tie? [them me]
  (= them me))

(defprotocol Scoreable
  (score [round] "Score a round of rock, paper, scissors"))

(defrecord ShapeRound [their-shape my-shape]
  Scoreable
  (score [{:keys [their-shape my-shape]}]
    (let [base-score (score-shape my-shape)]
      (+ base-score
         (cond
           (win? their-shape my-shape)
           win-bonus

           (tie? their-shape my-shape)
           tie-bonus

           :else
           loss-bonus)))))

(defrecord WinLossRound [their-shape outcome]
  Scoreable
  (score [{:keys [their-shape outcome]}]
    (score (->ShapeRound their-shape
                         (case outcome
                           :win
                           (defeats their-shape)

                           :loss
                           (defeated-by their-shape)

                           :tie
                           their-shape)))))

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

(comment (solution2 );; => 14470
)
