(ns rn-cljs.rn.nav.core
  (:require
   [reagent.core :as r]
   ["@react-navigation/native" :as nav]
   ["@react-navigation/stack" :as nav-stack]
   ["@react-navigation/material-top-tabs" :as nav-tab]))

(defonce Stack (nav-stack/createStackNavigator))
(def stack-navigator (-> Stack .-Navigator r/adapt-react-class))
(def stack-screen (-> Stack .-Screen r/adapt-react-class))
(def nav-container (-> nav/NavigationContainer r/adapt-react-class))
(defonce Tab (nav-tab/createMaterialTopTabNavigator))
(def tab-navigator (-> Tab .-Navigator r/adapt-react-class))
(def tab-screen (-> Tab .-Screen r/adapt-react-class))
(defonce nav-ref (atom nil))

(defn nav-wrapper [comp]
  (r/reactify-component
   (fn [{:keys [navigation route]}]
     (reset! nav-ref navigation)
     (comp (js->clj route :keywordize-keys true)))))
