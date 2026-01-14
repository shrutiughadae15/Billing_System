##  Project Purpose

In real-world billing applications (shops, supermarkets, billing software):

- Products need to be stored
- Bills must be generated
- Price, quantity, tax, and totals are calculated
- Data is stored securely in a database

This project implements these concepts using **Spring Boot + MySQL**.

##  Technologies Used

- Java – Core programming language
- Spring Boot – Backend framework
- Spring Data JPA – Database interaction
- MySQL – Relational database
- Maven – Dependency management
- REST APIs – Client–server communication

##  Project Architecture
### Layers Used:

1. Controller Layer
    - Handles HTTP requests
    - Exposes REST APIs
    - Communicates with Service layer

2. Service Layer
    - Contains business logic
    - Processes data before saving or returning it

3. Repository Layer
    - Interacts with the database
    - Uses Spring Data JPA

4. Entity Layer
    - Represents database tables
    - Maps Java classes to database records


##  Project Structure

Billing_System/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/spring/billing_software/
│ │ │ ├── controller → API endpoints
│ │ │ ├── service → Business logic
│ │ │ ├── repository → Database operations
│ │ │ └── entity → Database models
│ │ └── resources/
│ │ ├── application.properties → App configuration
│ │ ├── schema.sql → Table creation
│ │ └── data.sql → Initial data
│
├── pom.xml → Project dependencies
├── mvnw
└── mvnw.cmd


