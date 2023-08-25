(ns llm.cohere-api
  (:require [hato.client :as hc]))


(def hato-client (hc/build-http-client {:connect-timeout 10000
                                        :redirect-policy :always
                                        :ssl-context {:insecure? true}}))

(def request-defaults {:oauth-token (System/getenv "COHEREKEY")
                       :content-type :json
                       :as :json
                       :coerce :always
                       :throw-exceptions? false
                       :debug true})

(defn crear-embedding
  [texts-xs]
  (let [{:keys [status body]} (hc/post "https://api.cohere.ai/v1/embed"
                                       (merge request-defaults
                                              {:form-params {:texts texts-xs
                                                             :model "embed-multilingual-v2.0"}})
                                       hato-client)]
    (if (== status 200)
      (:embeddings body)
      (str "Hubo un error " status " \n Error: " (:message body)))))


(defn generar
  "Realiza petición a endpoint y devuelve respuesta. Referencia: https://docs.cohere.com/reference/generate "
  [prompt]
  (let [{:keys [status body]} (hc/post "https://api.cohere.ai/v1/generate"
                                       (merge request-defaults {:form-params {:prompt prompt
                                                                              :model "command-nightly"
                                                                              :max_tokens 1000
                                                                              :language "es"}})
                                       hato-client)]
    (if (== status 200)
      (-> body :generations first :text)
      (str "Hubo un error " status " \n Error: " (:message body)))))
;; Falta probar
(defn re-rankear
  "total_max_chunks (length of documents * max_chunks_per_doc)  debe ser menor a 10000"
  [consulta docs-vec]
  (let [{:keys [status body]} (hc/post "https://api.cohere.ai/v1/rerank"
                                       (merge request-defaults {:form-params {:model "embed-multilingual-v2.0"
                                                                              :query consulta
                                                                              :documents docs-vec
                                                                              :top_n 3
                                                                              :return_documents true}})
                                       hato-client)]
    (if (== status 200)
      (-> body :results)
      (str "Hubo un error " status " \n Error: " (:message body)))))

 (comment
 
   (crear-embedding ["Esta es una prueba tonta" "El cielo es azul, ¿verdad?" "Y cosas así, ¿qué más decir?" "¿Acepta acentos?"])
   (crear-embedding [151 54 616 5465 498 true :fees])
   (let [resp (generar "¿Cómo generan una respuesta los LLM ante una pregunta?")]
     (println (str "Numero de caracteres: " (count resp)))
     (clojure.pprint/pprint resp))
   (hc/post "https://api.cohere.ai/v1/generate"
            (merge request-defaults {:form-params {:prompt "¿Qué es cohere?"
                                                   :model "command-nightly"
                                                   :max_tokens 100}})
            hato-client))
