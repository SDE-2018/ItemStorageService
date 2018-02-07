## Storage SOAP web service for SkiResortItem entities

[https://iss-chernukha.herokuapp.com/skiresort?wsdl](https://iss-chernukha.herokuapp.com/skiresort?wsdl)

Please, take a look at Javadoc [here](https://merryhunter.github.io/trentinoskibot/).

To run the service you need to create a Recombee database and follow one of the option:  
1. Create a file `local.properties` and put it there, after uncomment code `soap.ws.skiresortitem.SkiResortServiceImpl:67`  
2. Implement class `RecombeeUtil` and method `getRecombeeClient` that returns a valid RecombeeClient.   

Then run `ant create.war`.

