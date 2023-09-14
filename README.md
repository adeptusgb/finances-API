# Finances API
API to keep track of expenses.

stores users and expenses. A user can have multiple expenses stored.

# User (api/v1/users)

* ### GET (api/v1/users)
    * get all users
* ### POST (api/v1/users)
    * create new user
* ### GET (api/v1/users/:userId)
    * get user by id
* ### PUT (api/v1/users/:userId)
    * update user
* ### DELETE (api/v1/users/:userId)
    * delete user

# Expense (api/v1/expenses)

* ### GET (api/v1/expenses)
    * get all expenses
* ### POST (api/v1/expenses)
    * create new expense for user
* ### GET (api/v1/expenses/:expenseId)
    * get expense by id
* ### PUT (api/v1/expenses/:expenseId)
    * update expense
* ### DELETE (api/v1/expenses/:expenseId)
    * delete expense