(ns cohere-try)

(require '[libpython-clj2.require :refer [require-python]]
         '[libpython-clj2.python :as py])

(require-python '[cohere :as c])