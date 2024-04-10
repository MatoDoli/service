Download files from github.
Open project in IntelliJ IDEA with min. JDK 17.
Must have running mysql database.
Create table customer with columns:
id int NOT NULL AUTO_INCREMENT,
user_id int,
title varchar(255),
body text(65535).
Set your mysql url, username and password to application.properties.
Run ServiceApplication.
Open Swagger link http://localhost:8090/swagger-ui/index.html.


