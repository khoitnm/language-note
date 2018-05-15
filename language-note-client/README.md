## FrontEnd Technologies
### Thymeleaf + Bootstrap + AngularJS 1. Why?<br/>
<p>Because I only want to focus on business model of this project. So all I want is to establish the program as soon as possible with my current knowledge.</p> 
<p>I don't want my passion and ideas for this project is cooled down because of some delay times for technologies concerns.</p> 

## Security
### OAuth2 + JWT token with Password flow. Why?
We need to separated the client and server because in the future, we also need a mobile or a Chrome/Firefox extension.<p/>
And the server will provide API for client applications. So we choose OAuth2 as the security protocol.
 
## Install Project
### Install JS libraries
Install NodeJS
```
sudo apt-get install -y nodejs
```

Install Bower
```
npm install -g bower 
```

Download client application by using following command line
```
cd src/main/resources/static
npm install 
```
Then run Maven install to build Java classes
```
mvn install -DskipTests
```

Open website on browser
```
http://localhost:8081/language-note-client/web/login
```