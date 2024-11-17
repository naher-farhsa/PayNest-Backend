# PayNest - Secure E-Wallet System

## Project Overview

**PayNest** implements a backend interface for **E-Wallet System** powered with  **Spring Boot** and **Spring Security**.
 
## Features

### 1. **Payment Wallet Implementation**
   - **Create Wallet**: Users can create a wallet by providing the necessary account details (account number, bank, wallet type).
   - **Deposit Funds**: Users can deposit funds into their wallets.
   - **Withdraw Funds**: Users can withdraw funds from their wallets, provided they have sufficient balance.
   - **Fund Transfer Between Wallets**: Users can transfer funds between wallets. Both sender and receiver wallets are validated before completing the transfer.

### 2. **User Authentication**
   - **JWT-Based Authentication**: User authentication is done using **JWT tokens** for secure and stateless communication.
   - **Spring Security 6**: The application uses Spring Security 6 to implement secure login, registration, and role-based access control (RBAC) for different users.

### 3. **ACID Compliance with JPA and Hibernate**
   - The application ensures **ACID properties** (Atomicity, Consistency, Isolation, Durability) for wallet transactions using **JPA** and **Hibernate**.
   - **Transaction Management**: All wallet-related operations (deposit, withdrawal, transfer) are managed in a transactional context to ensure integrity.

## Technologies Used

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security 6**
- **JWT (JSON Web Tokens)**
- **Spring Data JPA**
- **Hibernate ORM**
- **H2 Database** (for development purposes, can be swapped with MySQL or PostgreSQL)
- **Maven** (for dependency management)

### Cloneing Repository and Build Project

```bash
git clone https://github.com/naher-farhsa/PayNest-Backend.git
mvn clean install
mvn spring-boot:run
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

