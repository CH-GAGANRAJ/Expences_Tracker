# Expense Tracker API

A **Spring Boot REST API** for managing user expenses with **JWT authedication**, **role-based authorization**, and **category-based organization**. Built with **Java 17**, **Spring Boot 3** and **SQL Server**.

---

## Overview

The API allows users to:
-Register and log in securely
-Manage **expenses** (create, view, update, delete)
-Organize expenses into **categories**
-Support **pagination & filtering**
-Uses **JWT** and role-based security

---

# Technologies Used

- Java 17+
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- SQL Server
- Swagger / OpenAPI

---

## Authentication & Authorization

### Roles
- **ADMIN** -> full access (manage users, categories, and all expenses)
- **USER** -> limited access (only their own data)

### Auth Endpoints

| Endpoint | Method | Description
|-----------|---------|-----------|
|`/api/auth/register` | POST | Register new user |
|`/api/auth/login` | POST | Log in and receive JWT token |

---

## Expense Management

| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/api/expenses` | GET | Get all expenses (with filters) |
| `/api/expenses/{id}` | GET | Get expense by ID |
| `/api/expenses` | POST | Create new expense |
| `/api/expenses/{id}` | PUT | Update expense |
| `/api/expenses/{id}` | DELETE | Delete expense |

### Expense JSON Example

```json
{
  "amount": 120.50,
  "description": "Groceries",
  "date": "2025-10-06",
  "categoryId": 2,
  "userId": 1
}
```

---

## Categories

Each expense belongs to a category (e.g. Food, Travel, Bills).

| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/api/categories` | GET | List all categories |
| `/api/categories/{id}` | GET | Get category by ID |
| `/api/categories` | POST | Create category |
| `/api/categories/{id}` | PUT | Update category |
| `/api/categories/{id}` | DELETE | Delete category |

### Category JSON Example

```json
{
  "name": "Food"
}
```

---

## User Management (Admin Only)

| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/api/users` | GET | Get all users |
| `/api/users/{id}` | GET | Get user by ID |
| `/api/users/{id}` | PUT | Update user |
| `/api/users/{id}` | DELETE | Delete user |

---

## Running the Application

1. Clone repository:
   ```bash
   git clone https://github.com/mariatemp/ExpencesTracker
   cd ExpencesTracker
   ```

2. Run project:
   ```bash
   mvn spring-boot: run
   ```
3. Open Swagger UI:
   [Open Swagger UI](http://localhost:8080/swagger-ui/index.html)

4. Authorize using JWT:
   - Click **Authorize** in Swagger
   - Enter `Bearer <you-token>

---

## Future Improvements

- [ ] Add Unit & Integration Tests (JUnit / MockMvc)
- [ ] Add Expense Reports (monthly summaries, totals per category)
- [ ] Export to CSV/PDF
- [ ] Support recurring expenses
- [ ] Add frontend (Angular)

---

## Author

**MariaTemp**

[GitHub Repository](https://github.com/mariatemp/ExpencesTracker)

---
## License
This project is licensed under the **Apache 2.0 License** - see the LICENSE file for details.
