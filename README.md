# Bank Management System

This is a comprehensive bank management system developed using Spring Boot for the backend, Bootstrap and React for the front end, and PostgreSQL for the database.

## Features

- Create, update, and delete bank accounts
- User authentication and login system
- Transfer money between accounts
- Manage credit and debit cards
- Apply for loans and track their status
- View transaction history
- Two Roles : User and Admin

## Usage
Once the application is running, you can access its various features:
- Register and log in as a user.
- Create new bank accounts.
- Transfer money between accounts.
- Manage credit and debit cards.
- Apply for Credit and debit cards.
- Show Transaction History.
- Apply for loans and view their status.
- Check transaction history.

## Technologies Used
- Java(Spring Boot)
- Spring Data JPA
- Spring Security
- Lombok
- React (Front End)
- Bootstrap (Front End Styling)
- PostgreSQL (Database)

# Getting Started
# Running the Application
There are three way you can run this application
If you are familiar with docker(or docker installed in your device) and only want to focus on frontend than option-1 is recommanded

## Option - 1 : Run Using Docker

With This you don't have to download any jdk, java or any dependency
make sure you have the following installed:
- Docker
- Docker Compose

Now First clone docker-compose.yml file from this project's **main** branch
-- In Place of password enter password(any password will work)
and follow this steps


1. Open Terminal and navigate to the Project Directory
```shell
cd your-repo
```

2. Run Docker Compose
```shell
docker-compose up
```

3. You Can stop it with this
```shell
docker-compose down
```

## Option - 2 : Run Using This Repository

To set up the project locally, follow these steps:

1. Clone the repository:

```shell
git clone https://github.com/yourusername/bank-management.git
cd bank-management
```
2. Set up your PostgreSQL database and update the database configuration in the application.properties file.
3. Build and run the application:
```shell
   ./mvnw spring-boot:run
```
4. Access the application in your web browser at http://localhost:8080.


## Option - 3 : create new project from scrach
Go To [https://start.spring.io/]
  - choose maven as project structure
  - choose java as language
  - fill metadata
  - select java version(21 in this project)
  - add following dependency
    1. Spring Web
    2. Spring Data JPA
    3. Postgresql driver
    4. Spring Actuator(optional)
    5. Spring Security
    6. Validator
    7. Add swager Ui(Optional)
  - click on generate
