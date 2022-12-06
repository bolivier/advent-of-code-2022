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

(defn chars-required-for-unique-message
  "Partition the buffer into partitions of size `size`.

  Runs `index-of` against those.

  Not suitable for massive data."
  [buffer size]
  (let [idx (index-of starter? (partition size 1 buffer))]
    (+ size idx)))

(defn solution []
  (let [input (str/split (slurp "inputs/2022/day-6") #"")]
    (chars-required-for-unique-message input 4)))

(defn solution2 []
  (let [input (str/split (slurp "inputs/2022/day-6") #"")]
    (chars-required-for-unique-message input 14)))

(comment
  (solution)
  ;; => 1275
  (solution2)
  ;; => 3605
)
