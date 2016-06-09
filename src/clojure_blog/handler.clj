(ns clojure-blog.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


  (use '[clojure.string :ony (join split)])
  (use 'hiccup.core)
  (use 'hiccup.page)
  (use 'hiccup.element)


(defn make-title [title]
  (join "-"
        (split (.toLowerCase title) #"\s")))


(defn generic-page [content]
  (html5
    {:lang "en"}
    [:head
     [:title "the faintest ink"]
     (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")]
    [:body
     [:div {:class "container"} content ]]))


;; NOTE: Design decision ... Should get-article produce the html or just return a map containing the relevant information and then the calling function
;; will generate the html?

(defn get-article [id]
  (generic-page (html [:div [:h1 "Welcome to " id]
         (link-to {:class "btn btn-primary"} "/" "Back home")])))



(defn get-articles []
  ;; Retrieves all the articles as a list of maps. We'll use mongodb.
  ;; [{:title "title" :content "content" :date "date"}] <- something like that
  )


(defn home-page []
  (generic-page (html [:div {:class "all-articles"}]
                      [:h1 "Welcome to my blog!"]
                      [:h2 "List of articles"]
                      [:ul
                       (for [x (range 1 4)]
                         [:li (link-to (str "/articles/" x) (str "Article " x))] )])))


(defn not-found-page []
  (generic-page (html
                 [:h1 "Can't find that :("]
                 (link-to {:class "btn btn-primary"} "/" "Go back home"))))


(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/articles/:id" [id] (get-article id))
  (GET "/articles/:title" [title] (get-article title))
  (GET "/lowercase/:name" [name] (make-title name))
  (route/not-found (not-found-page)))


(def app
  (wrap-defaults app-routes site-defaults))
