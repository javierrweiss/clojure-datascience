(ns llm.cohere-clojure-sdk
  (:require [cohere.client :refer [generate generate-feedback generate-feedback-preference summarize embed classify chat
                                   tokenize detokenize rerank]]
            [clojure.java.io :as io]))

;; El SDK de Cohere-Clojure leerá la API KEY de Cohere desde una propiedad Java, llamada cohere.api.key, que debemos setear con nuestra llave.
(System/setProperty "cohere.api.key" (System/getenv "COHEREKEY")) 
 


(comment
  (generate :prompt "Explícame cómo crear un entorno efímero de programación" :temperature 1.5)

  (def chunks ["Given a user question, we’ll perform the following steps to use information stored in the vector database to answer their question using Retrieval Augmented Generation: Create an embedding vector for the user question.
    Use pgvector to perform a vector similarity search and retrieve the k nearest neighbors to the question embedding from our embedding vectors representing the blog content. In our example, we’ll use k=3, finding the three most similar embedding vectors and associated content.
    Supply the content retrieved from the database as additional context to the model and ask it to perform a completion task to answer the user question."
               "We supply helper functions to create an embedding for the user question and to get a completion response from an OpenAI model. We use GPT-3.5, but you can use GPT-4 or any other model from OpenAI.
We also specify a number of parameters, such as limits of the maximum number of tokens in the model response and model temperature, which controls the randomness of the model, which you can modify to your liking:"
               "We’ll define a function to process the user input by retrieving the most similar documents from our database and passing the user input, along with the relevant retrieved context to the OpenAI model to provide a completion response to."])
  
  (embed :texts chunks :model "embed-multilingual-v2.0")
  
  

  ,)

 


 