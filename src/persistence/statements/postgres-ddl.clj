(ns persistence.statements.postgres-ddl
  (:require [persistence.connections.postgres :as con]
            [honey.sql :as sql]))
(import java.sql.SQLException)

(defn activa-pgvector
  []
  (try
    (con/ejecuta-sentencia ["CREATE EXTENSION IF NOT EXISTS vector"])
    (catch SQLException e (str "Hubo un error " (.getMessage e)))))

(defn crea-tabla-luhmann-archive
  []
  (let [enunciado (sql/format {:create-table [:luhmann_archive :if-not-exists]
                               :with-columns [[:id :bigserial [:not nil]]
                                              [:referencia :varchar]
                                              [:pagina :int] 
                                              [:contenido :varchar]
                                              [:tokens :int]
                                              [:embedding [:vector 768]]
                                              [[:primary-key :id]]]})]
    (try 
      (con/ejecuta-sentencia enunciado)
      (catch SQLException e (str "Hubo un error " (.getMessage e))))))


(comment
  
  (activa-pgvector)

  (sql/format {:create-table [:luhmann_archive :if-not-exists]
               :with-columns [[:id :big-serial [:not nil]]
                              [:referencia :varchar]
                              [:pagina :int]
                              [:contenido :varchar]
                              [:tokens :int]
                              [:embedding [:vector 768]]
                              [[:primary-key :id]]]})
  
  (crea-tabla-luhmann-archive)
  
  (con/ejecuta-sentencia ["SELECT COUNT(*) FROM luhmann_archive"])
  (con/ejecuta-sentencia ["SELECT * FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema'"])
  ) 