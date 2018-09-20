# File based web server

This project is a small implementation of the multi-threaded web server used
to server static content pages. 

To  build the project use `mvn clean install`

Once successfully built your can run the project by running 

`java -jar target/webserver-1.0-SNAPSHOT.jar $serverPort $noThreads $webRoot`

Arguments are completely mandatory; if no or wrong arguments are passed in
the default values will be used
- server port: 8080
- number of threads: 3 
- web root: www 

This repository also contains a branch with support for keep alive connections as well as 
one that adds support for creating rest endpoints. At the current time I only supports 
getting data from the server and not passing along any parameters, but support will be provided
in the near future.