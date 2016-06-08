(ns clojure-blog.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

  (use 'hiccup.core)
  (use 'hiccup.page)
  (use 'hiccup.element)


(defn generic-page [content]
  (html5
    {:lang "en"}
    [:head
     [:title "the faintest ink"]
     (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")]
    [:body
     [:div {:class "container"} content ]]))



(defn get-article [id]
  (generic-page (html [:div [:h1 "Welcome to " id]
         (link-to {:class "btn btn-primary"} "/" "Back home")])))



(defn get-articles []
  ;; Retrieves all the articles list of maps
  ;; [{:title "title" :content "content" :date "date"}] <- something like that
  )


(defn home-page []
  (generic-page (html [:div {:class "all-articles"}]
                      [:h1 "Welcome to my blog!"]
                      [:h2 "List of articles"]
                      [:ul
                       (for [x (range 1 4)]
                         [:li x])])))


(defn not-found-page []
  (generic-page (html
                 [:h1 "Can't find that :("]
                 (link-to {:class "btn btn-primary"} "/" "Go back home"))))


(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/articles/:id" [id] (get-article id))
  (route/not-found (not-found-page)))


(def app
  (wrap-defaults app-routes site-defaults))
