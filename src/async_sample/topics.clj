(ns async-sample.topics
  (require [clojure.core.async :as async]))

(defn start-ch [name]
  (let [event-channel (async/chan)]
    (async/go
      (loop []
        (when-let [event (async/<! event-channel)]
          (println (str "[ch-" name ":event] " event))
          (recur)))
      (async/close! event-channel)
      (println (str "[ch-" name ":closed]")))
    (println (str "[ch-" name ":created]"))
    event-channel))

(def ch-1 (start-ch "1"))
(def ch-2 (start-ch "2"))

(def global-ch (async/chan))
(def global-mult (async/mult global-ch))

(defn test-topics []
  (async/tap global-mult ch-1)
  (async/tap global-mult ch-2)

  (async/>!! global-ch "global message")

  (async/close! global-ch))
