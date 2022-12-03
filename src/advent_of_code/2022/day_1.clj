(ns advent-of-code.2022.day-1
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [parse-int]]))

(defn sum [coll]
  (apply + coll))

(defn parse [file-contents]
  (->> (str/split file-contents #"\n\n")
       (map str/split-lines)
       (map #(map parse-int %))))

(defn get-elf-calorie-counts [elves]
 (map sum elves))

(defn solution []
  (let [file-contents (slurp "inputs/2022/day-1")
        elf-calories (get-elf-calorie-counts (parse file-contents))]
   (apply max elf-calories)))

(comment
  (solution);; => 69693
)

(defn solution2 []
  (let [file-contents (slurp "inputs/2022/day-1")
        elf-calories (get-elf-calorie-counts (parse file-contents))
        top-3-elf-calories (take 3 (reverse (sort elf-calories)))]
    (sum top-3-elf-calories)))

(comment
  (solution2) ;; => 200945
)
