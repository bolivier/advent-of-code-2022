(ns advent-of-code.2022.day-4-test
  (:require [clojure.test :as t]
            [advent-of-code.2022.day-4 :as sut]))

(t/deftest parse-test
  (t/is (=
         [[2 4] [6 8]]
         (sut/parse-line "2-4,6-8"))))
