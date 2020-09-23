(ns facade.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [re-pressed.core :as rp]
   [facade.events :as events]
   [facade.subs :as subs]
   ))

(defn dispatch-keydown-rules []
  (re-frame/dispatch
   [::rp/set-keydown-rules
    {:event-keys [[[::events/set-re-pressed-example "Hello, world!"]
                   [{:keyCode 72} ;; h
                    {:keyCode 69} ;; e
                    {:keyCode 76} ;; l
                    {:keyCode 76} ;; l
                    {:keyCode 79} ;; o
                    ]]
                  [[::events/set-re-pressed-example "ooo"]
                   [{:keyCode 79} ;; o
                    {:keyCode 79} ;; o
                    {:keyCode 79} ;; o
                    ]]
                  
                  [[::events/set-re-pressed-example "AaaaA"]
                   [{:keyCode 65} ;; a
                    {:keyCode 65} ;; a

                    ]]                  
                  [[::events/set-re-pressed-example "VV"]
                   [{:keyCode 86} ;; v
                    {:keyCode 86} ;; vv

                    ]]         
                  [[::events/set-re-pressed-example "pp"]
                   [{:keyCode 80} ;; p
                    {:keyCode 80} ;; p


                    ]]                  
           
                  [[::events/set-re-pressed-example (str "Name is" [::subs/name])]
                   [{:keyCode 80} ;; p
                   
                    ]]]
    
     :clear-keys
     [[{:keyCode 27} ;; escape
       ]]}]))

(defn display-re-pressed-example []
  (let [re-pressed-example (re-frame/subscribe [::subs/re-pressed-example])]
    [:re-com/v -   [re-com/alert-box
      :alert-type :warning
      :body "I'm a re-com warning box"]
     [:p
      "Re-pressed is listening for keydown events. However, re-pressed
      won't trigger any events until you set some keydown rules."]

     [:div
      [re-com/button
       :on-click dispatch-keydown-rules
       :label "set keydown rules"]]

     [:p
      [:span
       "After clicking the button, you will have defined a rule that
       will display a message when you type "]
      [:strong [:code "hello"]]
      [:span ". So go ahead, try it out!"]]

     (when-let [rpe @re-pressed-example]
       [:div
        [re-com/alert-box
         :heading
         "cAchtung!"

         :alert-type :warning
         :body rpe]])]))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name)
     :level :level1]))

(defn main-panel []
  [re-com/v-box
   :height "100%"
   :children [[title]
              [display-re-pressed-example]
              ]])
