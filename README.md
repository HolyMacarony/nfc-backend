# nfc-backend
Washing Machine backend

Adding Datasource via JBOSS-cli

connect
module add --name=com.mysql --resources=mysql-connector-java-8.0.18.jar --dependencies=javax.api,javax.transaction.api
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)
data-source add --jndi-name=java:/WashingDS --name=MySQLPool --connection-url=jdbc:mysql://localhost:3306/washing --driver-name=mysql --user-name=washing --password=***
 /subsystem=datasources/data-source=MySQLPool:test-connection-in-pool
 
 
 
 
 
 
 
 
 
 
 
 
 
 PS C:\Program Files\Java\jre1.8.0_241\bin> 
 .\keytool.exe -genkey -keyalg RSA -keystore server.keystore -storepass secret -keypass secret -storetype pkcs12 -validity 365  -dname "cn=Server Administrator,o=Acme,c=GB"
 
 SSL CERT
 auf server:
 root@euve265188:/etc/letsencrypt/live/washing.taking.pictures# 
 openssl pkcs12 -export -inkey privkey.pem -in fullchain.pem -name washing.taking.pictures -out washing.p12

auf client:
C:\Program Files\Java\jdk1.8.0_241\bin> .\keytool.exe -importkeystore -srckeystore washing.p12 -srcstoretype pkcs12 -destkeystore washing.jks








/subsystem=elytron/key-store=LocalhostKeyStore:add(path=server.keystore,relative-to=jboss.server.config.dir,credential-reference={clear-text="secret"},type=JKS)
/subsystem=elytron/key-manager=LocalhostKeyManager:add(key-store=LocalhostKeyStore,alias-filter=server,credential-reference={clear-text="secret"})
/subsystem=elytron/server-ssl-context=LocalhostSslContext:add(key-manager=LocalhostKeyManager)
/subsystem=undertow/server=default-server/https-listener=https:undefine-attribute(name=security-realm)
/subsystem=undertow/server=default-server/https-listener=https:write-attribute(name=ssl-context,value=LocalhostSslContext)