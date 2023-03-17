# DRONES

### Running the application

```shell
 ./gradlew run
```

### Changing the database
The project uses an H2 database, to change it for a more robust database, update the properties on the `application.properties` file
```` properties 
spring.datasource.url=jdbc:h2:mem:drones
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
````
_**Note:** Add also the dependency on the build.gradle.kts accordingly with your Database preference_

### Exercise Assumptions:
- If the drone `Serial` has more than `100` characters, the remaining characters will be truncated and the drone creation will be executed.
- For medication:
  - If the `name` has characters other than letters, numbers, `-` or `_` the service will not create the medication and will inform to the user accordingly.
  - If the `code` has lowercase letters, the system will change them to Uppercase and continue with the execution.
  - If the `code` has characters other than letters, numbers or `_` the service will not create the medication and will inform to the user accordingly.
  - The image attribute will store a path for the image.
- The medication quantity will be always available  on the database (Inventory will be out of the scope of the exercise).