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


## Curl examples

### Getting available drones: 
``` 
curl --location 'localhost:8080/drones/available'
```

### Registering a drone:
```
curl --location 'localhost:8080/drones/drone' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "serialNumber": "DJI0000008 DJI0000008 DJI0000008",
    "model": "Lightweight",
    "weightLimit": 170.0,
    "batteryCapacity": 50.0,
    "state": "IDLE"
}'
```
### Check drone battery:
```
curl --location 'localhost:8080/drones/drone/11/battery'
```
### Add a medication:
```
curl --location 'localhost:8080/medications/medication' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "name": "Product_1",
    "weight": 10.0,
    "code": "CODE01",
    "pathToImage": "/path/to/image1.jpg"
}'
```
### Get all registered medications:
_**Disclaimer:** This endpoint was not part of the exercise, but I considered important to be able to add medications without doing it by going directly to the database_
```
curl --location 'localhost:8080/medications/' \
--data ''
```
### Load a drone with medications:
```
curl --location 'localhost:8080/drones/drone/7/load_medications' \
--header 'Content-Type: application/json' \
--data '[
    {
        "medication": {
            "id": 1,
            "name": "Product_1",
            "weight": 10.0,
            "code": "CODE01",
            "pathToImage": "/path/to/image1.jpg"
        },
        "quantity": 1
    },
    {
        "medication": {
            "id": 2,
            "name": "Product_2",
            "weight": 20.0,
            "code": "CODE02",
            "pathToImage": "/path/to/image2.jpg"
        },
        "quantity": 1
    },
    {
        "medication": {
            "id": 3,
            "name": "Product_3",
            "weight": 30.0,
            "code": "CODE03",
            "pathToImage": "/path/to/image3.jpg"
        },
        "quantity": 1
    },
    {
        "medication": {
            "id": 4,
            "name": "Product_4",
            "weight": 40.0,
            "code": "CODE04",
            "pathToImage": "/path/to/image4.jpg"
        },
        "quantity": 1
    },
    {
        "medication": {
            "id": 5,
            "name": "Product_5",
            "weight": 50.0,
            "code": "CODE05",
            "pathToImage": "/path/to/image5.jpg"
        },
        "quantity": 3
    }
]'
```