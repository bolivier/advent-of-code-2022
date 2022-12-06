(ns advent-of-code.2022.day-4
  (:require [clojure.string :as str]
            [clojure.walk :as walk]
            [advent-of-code.utils :refer [parse-int]]
            [com.rpl.specter :as spt]))

(defn file-contents []
  #_"2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"
  (slurp "inputs/2022/day-4"))

(comment (def line "2-4,6-8"))
(defn- split-assignments [line]
  (str/split line #","))
(defn- split-assignment [assignment]
  (str/split assignment #"-"))
(defn- parse-section-ids [[start end]]
  [(parse-int start) (parse-int end)])

(defn parse-line [line]
  (into []
        (comp (map split-assignment)
              (map parse-section-ids))
        (split-assignments line)))

(comment (def input (file-contents)))
(defn parse [input]
  (map
   parse-line
   (str/split-lines input)))

(defn full-overlap [[start1 end1]
                    [start2 end2]]
  (or
   (<= start2 start1 end1 end2)
   (<= start1 start2 end2 end1)))

(defn no-overlap [[start1 end1]
                  [start2 end2]]
  (or (< end1 start2)
      (< end2 start1)))
(def some-overlap (complement no-overlap))

(defn solution []
  (let [sections (parse (file-contents))]
    (count (filter #(apply full-overlap %) sections))))
(comment
  (solution);; => 644
)

(defn solution2 []
  (let [sections (parse (file-contents))]
    (count (filter #(apply some-overlap %) sections))))
