# Online Book Store

## Table of Contents
- [Project Description](#project-description)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
    - [Docker](#docker)
    - [Local Installation](#local-installation)
    - [H2 Database](#h2-database)

## Project Description
The Online Book Store is a web application that allows users to browse, search, and purchase books online. The project includes the following domain models (entities):

- **User**: Contains information about the registered user including their authentication details and personal information.
- **Role**: Represents the role of a user in the system, such as admin or user.
- **Book**: Represents a book available in the store.
- **Category**: Represents a category that a book can belong to.
- **ShoppingCart**: Represents a user's shopping cart.
- **CartItem**: Represents an item in a user's shopping cart.
- **Order**: Represents an order placed by a user.
- **OrderItem**: Represents an item in a user's order.

### User Roles
- **Shopper (User)**: Can browse, search, and purchase books.
- **Manager (Admin)**: Manages book inventory and order statuses.

### Shopper Features
- **Join and Sign In**: Register and log in to the store.
- **Browse and Search Books**: View all books, detailed view of a book, and search by name.
- **Category Browsing**: View books by category.
- **Shopping Cart**: Add, view, and remove books in the cart.
- **Purchase Books**: Checkout and view past receipts.

### Manager Features
- **Manage Books**: Add, update, and remove books from the store.
- **Manage Categories**: Add, update, and remove categories.
- **Manage Orders**: Update order statuses.

## Technologies
- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Hibernate**
- **Liquibase**
- **MapStruct**
- **MySQL**
- **Docker**
- **Swagger**
- **Maven**
- **JUnit**
- **Dropbox API**
- **OAuth2**

## Getting Started

### Docker
To start the application using Docker:
1. Ensure you have Docker installed.
2. Create a `.env` file with the necessary environment variables:
   ```env
   MYSQLDB_ROOT_PASSWORD=root_password
   MYSQLDB_DATABASE=bookstore
   MYSQLDB_LOCAL_PORT=3306
   MYSQLDB_DOCKER_PORT=3306
   SPRING_LOCAL_PORT=8080
   SPRING_DOCKER_PORT=8080
   DEBUG_PORT=5005
   JWT_SECRET=your_jwt_secret
3. Build and start the containers:
```
docker-compose up --build
```
### Local Installation
To start the application with a locally installed MySQL database:
1. Ensure you have MySQL installed and running.
2. Update your application.properties with your local MySQL configuration:
```
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
spring.datasource.username=root
spring.datasource.password=root_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
jwt.expiration=1000000000000
jwt.secret=your_jwt_secret
```
3. Run the application
```
mvn spring-boot:run
```
### H2 Database
To start the application with an H2 in-memory database:
1. Update your application.properties to use H2:
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
jwt.expiration=1000000000000
jwt.secret=your_jwt_secret
```
2. Run the application:
```
mvn spring-boot:run
```
