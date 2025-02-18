#  Project Assessment

## Java Spring Boot Setup

This project is built using **Spring Boot**. Follow the steps below to set up and run the backend service.

---

## Prerequisites
- **Java 17+**
- **Maven**
- **An IDE** (IntelliJ IDEA, Eclipse, or VS Code with Java support)

## Installation Steps
1. **Clone the repository**:
   ```sh
   git clone https://github.com/aithalvishwas/assessment-BE
   ```
2. **Open the project in your preferred IDE**.

---

## Spring Boot Features

- **In-Memory H2 Database**: Quickly load and query user data.
- **Free Text Search**: Implemented using Hibernate Search for searching across multiple user fields.
- **Resilient Third-Party Integration**: Optimized and fault-tolerant calls to the external data source.
- **RESTful Endpoints**: Designed following REST best practices.
- **Environment Configurations**: Externalized configuration parameters to ease deployment in different environments.

---

## API Endpoints

| Method | Endpoint           | Description        |
|--------|--------------------|--------------------|
| GET    | `/api/users`       | Fetch all users    |
| GET    | `/api/users/{id}`  | Get a user by ID   |
| POST   | `/api/load`        | Load all user data |

---

