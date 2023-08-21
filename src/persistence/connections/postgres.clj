(ns persistence.connections.postgres
  (:require [next.jdbc :as jdbc]
            [next.jdbc.connection :as connection]))

(import com.zaxxer.hikari.HikariDataSource)

(defn ejecuta-sentencia
  "Recibe un vector con la consulta y devuelve un vector de mapas con los resultados"
  [sentence]
  (with-open [^HikariDataSource d (connection/->pool com.zaxxer.hikari.HikariDataSource
                                                     {:dbtype "postgres"
                                                      :dbname (System/getenv "POSTGRES_DB")
                                                      :username "postgres"
                                                      :password (System/getenv "POSTGRES_PASSWORD")
                                                      :host "localhost"
                                                      :port 8000})]
    (jdbc/execute! d sentence)))


(comment
  
(ejecuta-sentencia ["SELECT NOW()"])

  )