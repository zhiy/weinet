(ns weinet.api.sina
  (:require [clj-http.client :as client]
            [clojure.string :as s])
  (:use [hiccup.util]
        [clj-webdriver.taxi]))

(def api-key "4293240035")
(def consume-secret "eb59f40b252b8826646fd457ef9cb4d9")
(def authorize-url "https://api.weibo.com/oauth2/authorize")
(def access_token-url "https://api.weibo.com/oauth2/access_token")

(defn get-access-token [uname pass]
  (let [authorize-page (str (url authorize-url {:client_id api-key,:redirect_uri "http://127.0.0.1:8888"}))]
    (with-driver {:browser :firefox}
      (to authorize-page)
      (input-text "#userId" uname)
      (input-text "#passwd" pass)
      (click "a[class*='WB_btn_oauth formbtn_01']")
      ;; get the code
      (Thread/sleep 5000)
      (let [code (second (s/split (current-url) #"code="))]
        (prn code)
        (let [token (:access_token (:body (client/post access_token-url {:form-params {:client_id api-key,
                                                     :redirect_uri "http://127.0.0.1:8888",
                                                     :client_secret consume-secret,
                                                     :grant_type "authorization_code",
                                                     :code code
                                                     }}
                                                       )))]
          (quit)
          token)))))