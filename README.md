# Base Code for Login - Register - Logout

This project provides a **basic implementation** of a login, registration, and logout using Java with Maven and MySQL. It leverages the **MVC (Model-View-Controller)** pattern, similar to that used in Laravel.

## Prerequisites

Before you start using this project, make sure you have the following installed:
- **Java JDK** (Java Development Kit), if other versions are not working, try using **Java version "22.0.1"**
- **Maven** for dependency management
- **MySQL** or **XAMPP** for the database
- **Preferred IDE - Apache Netbeans**

## Getting Started

### Step 1: Clone the Repository

First, **clone this repository** to your local machine using:

```bash
git clone https://github.com/Resaizu/Base-Code-Java-Maven.git
```

or **download** the zip file

### Step 2: Set Up MySQL Connector
Download the MySQL Connector for Java (version 8.0.17) from:
[MySQL Connector/J 8.0.17 Archive](https://downloads.mysql.com/archives/c-j/)

**Assuming you used Windows**
- Recommended Product Version: 8.0.17
- Operating System: Platform Independent
- Download the ZIP file
- Extract the ZIP file and add the .jar file to your project’s library or configure it in your IDE to include it in the build path.

### Step 3: Database Configuration
If using MySQL or XAMPP, ensure the MySQL service is running.
- Create a new database named course_project, or use a database of your choice:
  - If you choose a different database, update the connection settings in the Repository/Database > find "**course_project**" and replace it using your database name.
- Create users table manually or use this:
```
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Step 4: Import Project
Import the project into your preferred IDE that supports Maven projects (e.g., Apache Netbeans, IntelliJ IDEA, Eclipse).

### Contributing
This project is primarily for educational purposes and is not well maintained. But Feel free to fork the repository, make improvements, and submit pull requests.

### License
This project is open source and available under the [MIT License](LICENSE).

### How To Use:

- **DB:**
  - `where`: Retrieves data from a column based on a search value. Supports comparison methods like `LIKE, !=, <>, >,` or refer to MySql manual.
    - Example: `User.DB.where("name", value, "LIKE").get();`
  - `orWhere`: Similar to `where`, but finds all possible data. Must have `where` before or it will throw an error.
    - Example: `User.DB.where("name", value, "LIKE").orWhere("username", value).get();`
  - `get`: Retrieves all data from a specific table.
    - Example: `User.DB.get();`
  - `create`: Stores data in the database.
    - Example: `User.DB.create(data);`
  - `update`: Updates data.
    - Example: `User.DB.update(data, User.id);`
  - `delete`: Removes specific data from the database.
    - Example: `User.DB.delete(User.id);`
