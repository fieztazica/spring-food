# Spring Food
___
Add the `secret.properties` file to `src/main/resources` to config the project correctly:
```yml
# Database connection properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring-food
spring.datasource.username=<your-db-username>
spring.datasource.password=<your-db-password>

# Mail service
spring.mail.host=<your-mail-host>
spring.mail.port=<your-mail-port>
spring.mail.username=<your-mail-username>
spring.mail.password=<your-mail-password>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Oauth2 service
spring.security.oauth2.client.registration.google.client-id=<google-client-id>
spring.security.oauth2.client.registration.google.client-secret=<google-client-secret>
```