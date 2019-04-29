(ns users.events
  )

(def CognitoUserPool (-> js/AmazonCognitoIdentity .-CognitoUserPool))
(def CognitoUserAttribute (-> js/AmazonCognitoIdentity .-CognitoUserAttribute))
(def CognitoUser (-> js/AmazonCognitoIdentity .-CognitoUser))
(def AuthenticationDetails (-> js/AmazonCognitoIdentity .-AuthenticationDetails))
(def AWSCognito js/AWSCognito)

