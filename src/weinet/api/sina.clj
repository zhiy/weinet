(ns weinet.api.sina
  (:require [clj-http.client :as client]
            [clj-webdriver.core :as webdriver])
  (:use [hiccup.util]))

(def api-key "4293240035")
(def consume-secret "eb59f40b252b8826646fd457ef9cb4d9")
(def authorize-url "https://api.weibo.com/oauth2/authorize")
(def access_token-url "https://api.weibo.com/oauth2/access_token")

(defn get-access-token [uname pass]
  (let [authorize-page (url authorize-url {:client_id api-key,:redirect_uri "http://127.0.0.1:8888"})
        ff (webdriver/new-driver {:browser :firefox})]
    (webdriver/get-url ff authorize-page)
    (webdriver/input-text "#userId" uname)
    (webdriver/input-text "#passwd" pass)
    (webdriver/click "a[class*='WB_btn_oauth formbtn_01']")))