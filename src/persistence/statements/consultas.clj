(ns persistence.statements.consultas
  (:require [persistence.connections.postgres :as con]
            [honey.sql :as sql]))
(import java.sql.SQLException)

(defn crear-registro
  "Debe ingresar un vector de vectores donde cada vector debe indicar valores para los siguientes campos en el mismo orden:
   referencia pagina contenido tokens embedding.
   Para el campo embedding, usar la función into-array o double-array para crear un array Java"
  [valores]
  (let [enunciado (sql/format {:insert-into :luhmann_archive
                               :columns [:referencia :pagina :contenido :tokens :embedding]
                               :values valores})]
    (try
      (con/ejecuta-sentencia enunciado)
      (catch SQLException e (str "Hubo un error " (.getMessage e))))))

;; Crear un índice cuando ya existan cierta cantidad de registros en la tabla. 
;; Ver https://www.timescale.com/blog/postgresql-as-a-vector-database-create-store-and-query-openai-embeddings-with-pgvector/

 
(defn buscar_similitudes
  [embeddings_consulta]
  (let [emb (if (instance? (Class/forName "[Ljava.lang.Double;") embeddings_consulta)
              embeddings_consulta
              (double-array embeddings_consulta))
        consulta (sql/format {:select :contenido
                              :from :luhmann_archive
                              :order-by [:embedding "<=>" emb]
                              :limit 3})]
    (try 
      (con/ejecuta-sentencia consulta)
      (catch SQLException e (str "Hubo un error " (.getMessage e))))))
 
(comment
  
(let [valores [["La sociedad de la sociedad" 90 "Acá va algo que diría Luhmann..." 5 (into-array [2.4343 0.343 1.4343])]]]
  (sql/format {:insert-into :luhmann_archive
               :columns [:referencia :pagina :contenido :tokens :embedding]
               :values valores}))
 
  (crear-registro [["La sociedad de la sociedad" 90 "Acá va algo que diría Luhmann..." 5 (into-array (take 768 (repeatedly #(rand 1))))]])

  (def arr (into-array (take 768 (repeatedly #(rand 1)))))
  
  (instance? (Class/forName "[Ljava.lang.Double;") arr) 
 
  (buscar_similitudes (into-array (take 768 (repeatedly #(rand 1)))))

  (con/ejecuta-sentencia (sql/format {:select :contenido
                                      :from :luhmann_archive
                                      :order-by [:embedding "<=>" arr]
                                      :limit 3}))
    
  ["SELECT contenido FROM luhmann_archive ORDER BY embedding <=> ? LIMIT 3" "?" (str (take 768 (repeatedly #(rand 1))) )]
  
  (sql/format {:select [[[:array (take 768 (repeatedly #(rand 1)))] :a]]})
(sql/sql-kw )

  )