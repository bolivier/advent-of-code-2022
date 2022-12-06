(ns advent-of-code.2022.day-5-test
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-code.2022.day-5 :as sut]))

(deftest create-command-test
  (is (= (sut/create-command "move 5 from 4 to 7")
         '(
            ))))
