(ns sierpinski.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn middle [[x1 y1] [x2 y2]]
  [(/ (+ x1 x2) 2) (/ (+ y1 y2) 2)])

; define 3 points forming
(def vertices
  (let [s 250]
    [[0 (- s)]
     [(* s (q/cos (/ Math/PI 6))) (* s (q/sin (/ Math/PI 6)))]
     [(* s (q/cos (/ (* 5 Math/PI) 6))) (* s (q/sin (/ (* 5 Math/PI) 6)))]]))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/stroke 217 65 71)
  (q/background 60 5 100)
; add sierpinski to the state
{:sierpinski (let [seed [0 0]
                   next #(middle % (rand-nth vertices))]
               (iterate next seed))})

(defn draw [state]
  ; access sierpinski from the state
  (let [sierpinski (:sierpinski state)]
    (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
                        (doseq [[x y] (take 50000 sierpinski)]
                          (q/point x y)))))

(q/defsketch sierpinski
  :title "Sierpinski"
  :setup setup
  :draw draw
  :size [600 600]
  :features [:keep-on-top]
  :middleware [m/fun-mode])

(defn -main [& args])
