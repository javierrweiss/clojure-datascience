(ns llm.documents
  (:require [libpython-clj2.require :refer [require-python]]
           [libpython-clj2.python :as py :refer [py. py.. py.-]]))

(def unstructured-loader (py/from-import langchain.document_loaders UnstructuredPDFLoader))
(def online-pdf-loader (py/from-import langchain.document_loaders OnlinePDFLoader))

(defn cargar-documento-no-estructurado
  [doc-path]
   (py. (unstructured-loader doc-path) load))


(comment
  #_(unstructured-loader "")
  (py. (online-pdf-loader "https://drive.google.com/file/d/1cQBtjXJ673bwnMAktB7trQ2ts_-xPeRJ/view?usp=drive_link")
       load)
  (cargar-documento-no-estructurado "https://drive.google.com/file/d/1cQBtjXJ673bwnMAktB7trQ2ts_-xPeRJ/view?usp=drive_link")
  
  ,)
