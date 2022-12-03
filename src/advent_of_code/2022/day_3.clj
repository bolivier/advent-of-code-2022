(ns advent-of-code.2022.day-3
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))

(def file-contents "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(defn halve [coll]
  (let [size (count coll)]
    [(into #{} (subvec coll 0 (int (/ size 2))))
     (into #{} (subvec coll (int (/ size 2))))]))

(defn parse [file-contents]
  (map
   (fn [line]
     (halve (str/split line #"")))
   (str/split-lines file-contents)))

(def get-priority
  {"a" 1
   "b" 2
   "c" 3
   "d" 4
   "e" 5
   "f" 6
   "g" 7
   "h" 8
   "i" 9
   "j" 10
   "k" 11
   "l" 12
   "m" 13
   "n" 14
   "o" 15
   "p" 16
   "q" 17
   "r" 18
   "s" 19
   "t" 20
   "u" 21
   "v" 22
   "w" 23
   "x" 24
   "y" 25
   "z" 26

   "A" 27
   "B" 28
   "C" 29
   "D" 30
   "E" 31
   "F" 32
   "G" 33
   "H" 34
   "I" 35
   "J" 36
   "K" 37
   "L" 38
   "M" 39
   "N" 40
   "O" 41
   "P" 42
   "Q" 43
   "R" 44
   "S" 45
   "T" 46
   "U" 47
   "V" 48
   "W" 49
   "X" 50
   "Y" 51
   "Z" 52})

(defn get-file-contents []
  (slurp "inputs/2022/day-3"))

(defn solution []
  (let [file-contents (get-file-contents)
        input (parse file-contents)
        overlapping-items (map (fn [[rucksack1 rucksack2]]
                                 (first (set/intersection rucksack1 rucksack2)))
                               input)]
    (apply + (map get-priority overlapping-items))))

(comment
  (solution);; => 8109
)
