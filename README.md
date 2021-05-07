# Summary
This project simulates a simple ATM machine server where multiple accounts and ATM units can be added in the database and controled via API calls. The project was created using Spring Initializr and uses an H2 databse in memory and JPA to do the persistence.

# How to run
Download this repository, go to \atm(where you can see the pom.xml file) and run the command `mvn clean install`, after you build the project you can run using the command `java -jar target/atm-0.0.1-SNAPSHOT.jar`. After the application starts use the endpoints in your localhost:8080. I left the H2 console enabled and you can access it [here](http://localhost:8080/h2-console/), to log in, you need to use `user: sa`, `JDBC URL: jdbc:h2:mem:testdb` and no password.

# Available endpoints
### Balance:
`POST` `<host>/balance`   
Sample Body:   
`{   
  "accountNumber": 123456789,   
  "pin": 1234   
}`

### Withdraw:
`POST` `<host>/withdraw`   
Sample Body:   
`{   
  "accountNumber": 123456789,   
  "pin": 1234,   
  "withdrawValue":200,   
  "unitId":1      
}`

# Scope for Improvements
-Values are current int, they can be changed to decimal to reflect real currency   
-Rollback system or autocommit off are not included and they can offer a better safety agains failures   
-Logging can be added to create a history transactions and failures   
-The pin value is not encrypted and never expire, this can be replaced by tokens   
-The app is using errors in a very primitive way, a custom exception can improve the flow of errors inside the layers used   
-The cash notes are controled by independent variables, this can be replaced by an array and reduce unescessary variables   
-The app is asynchronous, and this is not ideal to an ATM manager   
-The validation for notes availability can be moved inside Unit.withdraw() and avoid the need to be called by future metods calling Unit.withdraw()   
-JUnits can be added to cover the repositories   
-Swagger can be added   