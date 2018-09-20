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

