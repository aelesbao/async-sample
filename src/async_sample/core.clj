(ns async-sample.core
  (:gen-class)
  (require [async-sample.topics :as topics]))

(defn -main [& args]
  (topics/test-topics))
