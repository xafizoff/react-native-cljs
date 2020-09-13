(ns rn-cljs.rn.util
  (:require [react-native :as rn]))

(defn make-scale [psd-width]
  (let [window (-> rn/Dimensions (.get "window"))
        window-width (.-width window)]
    (fn [size]
      (-> window-width (* size) (/ psd-width)))))

(def ^:private android-depth
  {:umbra [{:x "0", :y "2", :blur "1", :spread "-1"}
           {:x "0", :y "3", :blur "1", :spread "-2"}
           {:x "0", :y "3", :blur "3", :spread "-2"}
           {:x "0", :y "2", :blur "4", :spread "-1"}
           {:x "0", :y "3", :blur "5", :spread "-1"}
           {:x "0", :y "3", :blur "5", :spread "-1"}
           {:x "0", :y "4", :blur "5", :spread "-2"}
           {:x "0", :y "5", :blur "5", :spread "-3"}
           {:x "0", :y "5", :blur "6", :spread "-3"}
           {:x "0", :y "6", :blur "6", :spread "-3"}
           {:x "0", :y "6", :blur "7", :spread "-4"}
           {:x "0", :y "7", :blur "8", :spread "-4"}
           {:x "0", :y "7", :blur "8", :spread "-4"}
           {:x "0", :y "7", :blur "9", :spread "-4"}
           {:x "0", :y "8", :blur "9", :spread "-5"}
           {:x "0", :y "8", :blur "10", :spread "-5"}
           {:x "0", :y "8", :blur "11", :spread "-5"}
           {:x "0", :y "9", :blur "11", :spread "-5"}
           {:x "0", :y "9", :blur "12", :spread "-6"}
           {:x "0", :y "10", :blur "13", :spread "-6"}
           {:x "0", :y "10", :blur "13", :spread "-6"}
           {:x "0", :y "10", :blur "14", :spread "-6"}
           {:x "0", :y "11", :blur "14", :spread "-7"}
           {:x "0", :y "11", :blur "15", :spread "-7"}]
   :penumbra [{:x "0", :y "1", :blur "1", :spread "0"}
              {:x "0", :y "2", :blur "2", :spread "0"}
              {:x "0", :y "3", :blur "4", :spread "0"}
              {:x "0", :y "4", :blur "5", :spread "0"}
              {:x "0", :y "5", :blur "8", :spread "0"}
              {:x "0", :y "6", :blur "10", :spread "0"}
              {:x "0", :y "7", :blur "10", :spread "1"}
              {:x "0", :y "8", :blur "10", :spread "1"}
              {:x "0", :y "9", :blur "12", :spread "1"}
              {:x "0", :y "10", :blur "14", :spread "1"}
              {:x "0", :y "11", :blur "15", :spread "1"}
              {:x "0", :y "12", :blur "17", :spread "2"}
              {:x "0", :y "13", :blur "19", :spread "2"}
              {:x "0", :y "14", :blur "21", :spread "2"}
              {:x "0", :y "15", :blur "22", :spread "2"}
              {:x "0", :y "16", :blur "24", :spread "2"}
              {:x "0", :y "17", :blur "26", :spread "2"}
              {:x "0", :y "18", :blur "28", :spread "2"}
              {:x "0", :y "19", :blur "29", :spread "2"}
              {:x "0", :y "20", :blur "31", :spread "3"}
              {:x "0", :y "21", :blur "33", :spread "3"}
              {:x "0", :y "22", :blur "35", :spread "3"}
              {:x "0", :y "23", :blur "36", :spread "3"}
              {:x "0", :y "24", :blur "38", :spread "3"}]
   :ambient [{:x "0", :y "0", :blur "0", :spread "0"}
             {:x "0", :y "1", :blur "3", :spread "0"}
             {:x "0", :y "1", :blur "5", :spread "0"}
             {:x "0", :y "1", :blur "8", :spread "0"}
             {:x "0", :y "1", :blur "10", :spread "0"}
             {:x "0", :y "1", :blur "14", :spread "0"}
             {:x "0", :y "1", :blur "18", :spread "0"}
             {:x "0", :y "2", :blur "16", :spread "1"}
             {:x "0", :y "3", :blur "14", :spread "2"}
             {:x "0", :y "3", :blur "16", :spread "2"}
             {:x "0", :y "4", :blur "18", :spread "3"}
             {:x "0", :y "4", :blur "20", :spread "3"}
             {:x "0", :y "5", :blur "22", :spread "4"}
             {:x "0", :y "5", :blur "24", :spread "4"}
             {:x "0", :y "5", :blur "26", :spread "4"}
             {:x "0", :y "6", :blur "28", :spread "5"}
             {:x "0", :y "6", :blur "30", :spread "5"}
             {:x "0", :y "6", :blur "32", :spread "5"}
             {:x "0", :y "7", :blur "34", :spread "6"}
             {:x "0", :y "7", :blur "36", :spread "6"}
             {:x "0", :y "8", :blur "38", :spread "7"}
             {:x "0", :y "8", :blur "40", :spread "7"}
             {:x "0", :y "8", :blur "42", :spread "7"}
             {:x "0", :y "9", :blur "44", :spread "8"}
             {:x "0", :y "9", :blur "46", :spread "8"}]})

(defn- round [f]
  (/ (.round js/Math (* 100 f)) 100))

(defn- interpolate [i a b a2 b2]
  (-> (- i a) (* (- b2 a2)) (/ (- b a)) (+ a2)))

(defn elevation [depth]
  (let [depth (dec depth)
        s (-> android-depth :penumbra (nth depth))
        y (if (== (:y s) 1) 1 (quot (:y s) 2))]
    {:shadow-color "#000"
     :shadow-offset {:width 0 :height y}
     :shadow-opacity (-> (interpolate depth 1 24 0.2 0.6) round)
     :shadow-radius (-> (interpolate (:blur s) 1 38 1 16) round)
     :elevation (inc depth)}))
