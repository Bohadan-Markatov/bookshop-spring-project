Online Book Store
Project Description
The Online Book Store application is designed to facilitate the buying and selling of books. It is developed using Spring Boot and will be implemented in phases. The application consists of several domain models (entities):

User: Contains information about registered users, including authentication details and personal information.
Role: Represents the role of a user in the system, such as admin or user.
Book: Represents a book available in the store.
Category: Represents a category that a book can belong to.
ShoppingCart: Represents a user's shopping cart.
CartItem: Represents an item in a user's shopping cart.
Order: Represents an order placed by a user.
OrderItem: Represents an item in a user's order.
People Involved
Shopper (User)
A user who browses books, adds them to their shopping cart, and purchases them.

Manager (Admin)
A user who manages the book inventory and monitors sales.

Shopper Capabilities
Join and Sign In:
Register to join the store.
Sign in to browse and purchase books.
Browse and Search for Books:
View all available books.
View details of a specific book.
Search for books by name.
Browse Bookshelf Sections:
View all bookshelf sections.
View books in a specific section.
Use the Shopping Cart:
Add a book to the shopping cart.
View the shopping cart.
Remove a book from the shopping cart.
Purchase Books:
Purchase all books in the shopping cart.
View past receipts.
View Receipts:
View all books on a receipt.
View details of a specific book on a receipt.
Manager Capabilities
Manage Books:
Add a new book to the store.
Update details of an existing book.
Remove a book from the store.
Manage Bookshelf Sections:
Create a new bookshelf section.
Update details of a section.
Remove a section.
Manage Receipts:
Update the status of a receipt (e.g., "Shipped", "Delivered").
Getting Started
To get started with the project, clone the repository and follow the setup instructions.

bash
Копировать код
git clone <repository-url>
cd online-book-store
Prerequisites
Java 21
MySQL
Docker (optional, for containerized deployment)
Installation
Set up the MySQL database and update the application.properties file with your database details.
Build and run the application:
bash
Копировать код
./mvnw clean install
./mvnw spring-boot:run
Usage
The application will be available at http://localhost:8080.
