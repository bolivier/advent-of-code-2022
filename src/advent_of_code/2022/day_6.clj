(ns advent-of-code.2022.day-6
  (:require [clojure.string :as str]))

(defn first-where [f coll]
  (if (f (first coll))
    (first coll)
    (recur f (rest coll))))

(defn starter?
  "Returns true when `marker` is a valid starter for the signal"
  [marker]
  (= (count marker)
     (count (into #{} marker))))

(defn index-of [f coll]
  (loop [coll coll
         idx 0]
    (if (empty? coll)
      nil
      (if (f (first coll))
        idx
        (recur
         (rest coll)
         (inc idx))))))

(defn solution []
  (let [input (str/split (slurp "inputs/2022/day-6") #"")
        #_#_input "bvwbjplbgvbhsrlpgdmjqwftvncz"
        idx (index-of starter?
                      (partition 4 1 input))]
    (+ 4 idx)))

(defn solution2 []
  (let [input (str/split (slurp "inputs/2022/day-6") #"")
        #_#_input "bvwbjplbgvbhsrlpgdmjqwftvncz"
        idx (index-of starter?
                      (partition 14 1 input))]
    (+ 14 idx)))

(comment
  (solution)
  ;; => 1275
  (solution2)
  ;; => 3605
)
