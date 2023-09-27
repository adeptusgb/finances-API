
# Finances API  

<div align=left>
<img src=https://img.shields.io/badge/java-E9498A.svg?style=for-the-badge&logo=openjdk&logoColor=white />
<img src=https://img.shields.io/badge/spring-E9498A.svg?style=for-the-badge&logo=spring&logoColor=white />
<img src=https://img.shields.io/badge/postgres-E9498A.svg?style=for-the-badge&logo=postgresql&logoColor=white />
<img src=https://img.shields.io/badge/JWT-E9498A?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white />
</div>

---

API to keep track of expenses.

## Usage

* ### Auth [ api/v1/auth ]
		
		POST /login - Log to the application
		
		POST /register - Register a new user
		
* ### User [ api/v1/users ]

		GET / - Get all users  

		GET /{userId} -  Get user by ID
		
		PUT /{userId} - Update user
		
		DELETE /{userId} - Delete user

* ### Expense [ api/v1/users/{userId}/expenses ]

		GET / - Get all expenses from a user
		
		POST / - Create expense for a user
		
		GET /{expenseId} -  Get an expense from a user
		
		PUT /{expenseId} - Update an expense from a user
		
		DELETE /{expenseId} - Delete expense from a user

## UML Diagram

```mermaid
erDiagram
    User {
        Long id PK
        String username
        String password 
        Role role
    }

    Expense {
        Long expenseId PK
        String name
        BigDecimal cost
        Date date
        Long userId FK
    }

    User ||--o{ Expense : has
```
