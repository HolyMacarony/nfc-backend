# nfc-backend
Washing Machine backend

Adding Datasource via JBOSS-cli

connect
module add --name=com.mysql --resources=mysql-connector-java-8.0.18.jar --dependencies=javax.api,javax.transaction.api
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)
data-source add --jndi-name=java:/WashingDS --name=MySQLPool --connection-url=jdbc:mysql://localhost:3306/washing --driver-name=mysql --user-name=washing --password=***
 /subsystem=datasources/data-source=MySQLPool:test-connection-in-pool