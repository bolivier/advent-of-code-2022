(ns advent-of-code.2022.day-5
  (:require [clojure.string :as str]))

(def table-string "        [C] [B] [H]
[W]     [D] [J] [Q] [B]
[P] [F] [Z] [F] [B] [L]
[G] [Z] [N] [P] [J] [S] [V]
[Z] [C] [H] [Z] [G] [T] [Z]     [C]
[V] [B] [M] [M] [C] [Q] [C] [G] [H]
[S] [V] [L] [D] [F] [F] [G] [L] [F]
[B] [J] [V] [L] [V] [G] [L] [N] [J]
 1   2   3   4   5   6   7   8   9
")

(defn space? [char]
  (= \space char))

(def lines (str/split-lines table-string))
(def line (first lines))

(defn done? [table]
  (every? empty? table))

(defn char->int
  "Convert a char representation of an int to an actual int

  Should take a char, like `\1` or `\2` and convert that into an int, here `1` and `2`"
  [c]
  (- (int c) (int \0)))

(defn not-needed? [elm]
  (or (space? elm)
      (nil? elm)))

(defn create-stacks [table-string]
  (loop [table (str/split-lines table-string)
         stacks {}]
    (if (every? empty? table)
      stacks
      (let [column (map second (map
                                #(take 3 %)
                                table))
            stack-values (butlast column)
            label (char->int (last column))]
        (prn column)
        (recur
         (map #(drop 4 %) table)
         (assoc stacks label (remove not-needed? stack-values)))))))

(defn create-command [input]
  (let [[_ amount* from* to*] (re-matches
                               #"move (\d+) from (\d+) to (\d+)"
                               input)]
    {:amount (Integer/parseInt amount*)
     :from (Integer/parseInt from*)
     :to (Integer/parseInt to*)}))

(def ^:dynamic *machine*
  "CrateMover 9000 moves one box at a time, but CrateMover 9001 moves multiple
  boxes at a time.

  This should be bound to the value of the current machine: either `9000` or `9001`"
  9000)

(defn execute-command
  "Create an infinite sequence of moving items from `from` to `to` and take the
  `amount`th element from that sequence, representing `amount` items moved."
  [state {:keys [amount from to]}]

  (let [iterate-fn (cond
                     (= *machine* 9000)
                     (fn [state]
                       (let [from-stack (get state from)
                             to-stack   (get state to)]
                         (assoc state
                                from (rest from-stack)
                                to (conj to-stack (first from-stack)))))

                     (= *machine* 9001)
                     (fn [state]
                       (let [from-stack (get state from)
                             to-stack   (get state to)]
                         (assoc state
                                from (rest from-stack)
                                to (conj to-stack (first from-stack))))))]
    (nth (iterate iterate-fn state) amount)))

(defmulti execute-command (fn [_ _] *machine*))

(defmethod execute-command 9000 [state {:keys [amount from to]}]
  (nth (iterate (fn [state]
                  (let [from-stack (get state from)
                        to-stack   (get state to)]
                    (assoc state
                           from (rest from-stack)
                           to (conj to-stack (first from-stack)))))
                state)
       amount))

(defmethod execute-command 9001 [state {:keys [amount from to]}]
  (let [from-stack (get state from)
        to-stack   (get state to)
        [crates-to-move new-from-stack] (split-at amount from-stack)]
    (assoc state
           from new-from-stack
           to (concat crates-to-move to-stack))))

(defn read-input []
  (slurp "inputs/2022/day-5"))

(defn parse-input [input]
  (let [[table commands] (str/split input #"\n\n")]
    {:raw-table table
     :raw-commands commands}))

(defn solution []
  (let [{:keys [raw-table raw-commands]} (parse-input (read-input))
        commands                         (map create-command(str/split-lines raw-commands))
        stacks                           (create-stacks raw-table)
        final-stacks                     (reduce execute-command stacks commands)]

    (str/join ""
              (map
               (fn [stack-num]
                 (first (get final-stacks stack-num)))
               (range 1 10)))))
(comment
  (solution) ;; => "VJSFHWGFT"

  (binding [*machine* 9001]
    (solution))
;; => "LCTQFBVZV"


)
