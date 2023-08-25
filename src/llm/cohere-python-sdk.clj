(ns llm.cohere-python-sdk
  (:require [libpython-clj2.python :as py]))

;; Si queremos usar el SDK para Python, debemos hacer lo siguiente:

(require '[libpython-clj2.require :refer [require-python]]
         '[libpython-clj2.python :as py :refer [py. py.. py.-]])

(require-python '[cohere :as c]) 
(require-python '[builtins :as python])
 
(def py-client (c/Client (System/getenv "COHEREKEY")))
 
;; El endpoint >embed< recibe dos parámetros en su body-params, a saber, texts y model. El primero es un array de strings y
;; su tamaño máximo es de 96. Cada texto debe estar por debajo de los 512 tokens (¿Cómo calculo el número de tokens?). 
;;  El otro parámetro es el modelo y los modelos disponibles son embed-english-v2.0 (default) 4096 (esta es la dimensión del vector), 
;; embed-english-light-v2.0 1024 y embed-multilingual-v2.0 768. Para más información: https://docs.cohere.com/reference/embed

;; Tiene que ser una lista de Python array de strings a pasarle a embed
(def xs (python/list))
(py. xs append "palabra")
(py. xs append "palabra2")

(py. py-client embed xs)

(-> (py/$a py-client embed xs)
    (py.- embeddings)) 


