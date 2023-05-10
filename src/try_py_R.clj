(ns try-py-R 
  (:require [clojisr.v1.r :as r]
            [tech.v3.dataset :as tc]))

(require '[libpython-clj2.require :refer [require-python]]
         '[libpython-clj2.python :as py])

;;;  python
(require-python '[os :as os])
(os/getcwd)

(require-python '[numpy :as np])

(def a (np/array [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]))
(println a)
(np/shape a)
(np/amax a)


;; R
(require '[clojisr.v1.r :as r :refer [r require-r]])
(r "1+1")

(-> (r "list(vector = c(1,2,44,43,53), a = 23, b = \"a\")")
    (r/r->clj))

(-> "data.frame(a = 1:10, b = c(\"a\", \"b\", \"c\", \"d\", \"e\", \"f\", \"g\", \"h\", \"i\", \"j\"))"
    (r/r->clj))

(time (r/r+ 32 43 43 53 43 34 5 3 453 43 5 3))
(time (+ 32 43 43 53 43 34 5 3 453 43 5 3))
  
(time (r "Reduce(\"+\", c(3, 4, 34, 3, 2331, 32, 34, 34, 13, 1))"))
(time (reduce + [3 4 34 3 2331 32 34 34 13 1]))

((r "Reduce") "+" [22 3 23 2 3 2])

(let [row-means (r "function(data) rowMeans(data)")]
  (->> {:x [1 2 3], :y [4 5 6]}
       tc/->dataset
       row-means
       r/r->clj))

(require-r '[base :as base-r])

;;;  numpy -> clj -> R
(def r-matrix
 (-> (np/array [[1 2 3 4] [5 6 7 8] [9 10 11 12]])
     (py/->jvm)
     (r/clj->java->r)
     (base-r/simplify2array)
     (base-r/t)))

(println
 (base-r/dim r-matrix))


(require '[libapl-clj.apl :as apl])

(apl/+ [1 2 3] [4 5 6])

(apl/display! (apl/+ [1 2 3] [4 5 6]))
