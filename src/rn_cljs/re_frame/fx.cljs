(ns rn-cljs.re-frame.fx
  (:require
   #_[react-native :as rn]
   [rn-cljs.rn.nav.core :refer (nav-ref)]
   [re-frame.core :refer [reg-fx reg-event-fx]]))

(print [::fx :loaded true])

(reg-event-fx
 ::navigate
 (fn [_ [_ screen params]]
   {::navigate [screen params]}))

(reg-fx
 ::navigate
 (fn [[screen params]]
   #_(print [::navigate screen params])
   (when @nav-ref
     (.navigate @nav-ref screen (clj->js params)))))

(reg-event-fx
 ::nav-back
 (fn [_ _]
   {::nav-back true}))

(reg-fx
 ::nav-back
 (fn [_]
   (when @nav-ref
     (.goBack @nav-ref))))
