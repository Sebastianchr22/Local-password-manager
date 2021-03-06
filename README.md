# Local Password Manager
 A complete open-source password manager with local password encrypted storage.

 It allows you to create and store passwords, locally and securely.
 Keep your data, control your data. 
 Only you can unlock your files, and noone else has them.
 
 ![In-app Password manager vault](Images/vault2.png)
 
 
 ## Download
 The password manager can be downloaded [from here](Downloads/LocalPasswordManager.jar), or compiled from the source code provided.
 
 
 ## Open source
 I urge every user to browse through the source code of any open source project to ensure any claims made, lucky for you this project is fairly light on code.
 
 
 ## Features
 These are the features of the password manager
 
 ### Storing password
 When a pasword (site key) is stored it is associated with a username (any string), a password (any string) and a website/name of the service to which the site key is meant.
 All this is encrypted and stored locally on your own machine without ever touching the internet.

![In-app Password manager vault](Images/create.png)
 
 
 
 ### Generating password
 The password manager allows for the generation of new passwords, these can be selected to include numbers and or special characters. Furthermore, you set the length of your desired password.
 Once generated the password is kept in the generate tab until the generator settings are changed or the refresh button is pressed.
 A generated password can be copied by simply clicking the 'copy to clipboard' button.

![In-app Password manager vault](Images/generate.png)
 
 
 
 ### Locking
 The password manager contains a button to lock up the application requiring the user to login again. This is to avoid the need of closing the password manager once unattended. 
  
![In-app Password manager vault](Images/login.png)
 
 
 
 ### Compiled .jar file
 Once downloaded, use your terminal to enter
 
 ```java -jar <folder>/LocalPasswordManager.jar ```
