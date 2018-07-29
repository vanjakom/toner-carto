(ns simple-tile-server.server
  (:gen-class)
  (:require
    [clj-common.logging :as logging]
    [clj-common.jvm :as jvm]
    [clj-common.http-server :as server]
    [clj-common.http :as http]
    compojure.core))

(def handler
  (compojure.core/routes
    (compojure.core/GET
      "/explore/toner"
      _
      (fn [request]
        {
          :status 200
          :headers {
                     "ContentType" "text/html"}
          :body (jvm/resource-as-stream ["html" "explore" "toner.html"])}))
    (compojure.core/GET
      "/explore/toner-lines"
      _
      (fn [request]
        {
          :status 200
          :headers {
                     "ContentType" "text/html"}
          :body (jvm/resource-as-stream ["html" "explore" "toner-lines.html"])}))
    (compojure.core/GET
      "/tile/:style/:zoom/:x/:y" [style zoom x y]
      (cond
        (or (= style "toner-lines") (= style "toner"))
        (do
          (logging/report {:style style :zoom zoom :x x :y y})
          (if-let [response (http/get-as-stream (str "http://toner-renderer:8080/" style "/" zoom "/" x "/" y ".png"))]
            {
              :status 200
              :headers {
                         "ContentType" "image/png"}
              :body response}
            {
              :status 404 }))
        :else
        {
          :status 404 }))
    (compojure.core/GET
      "/status"
      _
      (fn [request]
        {:status 200
         :body "ok\n"}))))

(defn -main [& args]
  (server/create-server 80 (var handler)))
