# assignment
Technology used in this project <br>
<ul>
<li>Java</li>
<li>Spring cloud</li>
<li>Spring security</li>
<li>Activemq</li>
<li>H2 Database</li>
</ul>
<br>


Steps to run all projects <br>
1 : Install Activemq : https://activemq.apache.org/version-5-getting-started.html <br><br>
2 : Run Activemq : verify http://localhost:8161/admin (Username : admin , Password : admin) <br><br>
3 : Goto discovery-server project and run command : <br> <p><b> mvn clean install </b>and<b> mvn spring-boot:run</b>  (verify http://localhost:7070)<br><br>
4 : Goto authorization-service project and run command : <br><p><b> mvn clean install </b>and<b> mvn spring-boot:run</b> <br><br>
    To check "user" table in H2 database use http://localhost:9090/h2-console/ url. (H2 database password : <b>password</b>)<br>
    Add one record to user table <br> 
    
    
    insert into USER values (1,'$2a$10$FhAL6iexaBBKjG03nGcu9eZawQ1hyfq9SGdXcIEM2J82FqQ4TBU0C','demo');
    
    
    
5 : Goto authorization-service project and run command : <br><p><b> mvn clean install </b>and<b> mvn spring-boot:run</b> <br><br>
    To check "profile" table in H2 database use http://localhost:6060/h2-console/ url. (H2 database password : <b>password</b>)<br>
    
    
    Note : Activemq should be up and running 
    
    
<h3> Test project : </h3><br>
Use following endpoints <br>
1 : Login API method POST - <br> http://localhost:9090/login?username=demo&password=123<br><br>
2 : Save profile API method POST - <br> http://localhost:9090/profile <br> 
   request Json :  { 
	        "address":"38",
	        "mobile" : "987987987987"
    } <br><br>
3 : Update profile API method PUT - <br> http://localhost:9090/profile <br> 
   request Json :  { "userId":"1",
	        "address":"38",
	        "mobile" : "987987987987"
    } <br><br>
4 : Delete profile API method DELETE - <br> http://localhost:9090/profile?userId=1 <br> <br>
   
5 : Logout : <br> http://localhost:9090/logout    
    
