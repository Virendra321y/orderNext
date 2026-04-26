# 🛒 E-Commerce Order Management API (Spring Boot)

---

## 📌 Overview

This project is a **simple and scalable E-Commerce backend system** built using:

* Java + Spring Boot
* Spring Data JPA
* MySQL (or any relational database)

### 🎯 Goal

To demonstrate:

* Clean backend design
* Order placement flow
* Avoiding heavy JPA mappings
* Using **DB-level relationships (Foreign Keys via IDs)**

---

## 🧠 High-Level Architecture

```id="arch1"
Client (Postman / UI)
        ↓
Controller (REST APIs)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA)
        ↓
Database (MySQL)
```

---

## 🧩 Core Modules

| Module    | Responsibility    |
| --------- | ----------------- |
| Customer  | User identity     |
| Address   | Delivery location |
| Product   | Product catalog   |
| Order     | Transaction       |
| OrderItem | Order details     |

---

## 🗄️ Database Design (Most Important)

### 🔹 Tables Overview

```id="db1"
customers
addresses
products
orders
order_items
```

---

### 🔹 1. customers

```sql id="db2"
customer_id (PK)
name
email
phone
```

---

### 🔹 2. addresses

```sql id="db3"
id (PK)
customer_id (FK)
street
city
state
zip_code
```

👉 One customer can have multiple addresses

---

### 🔹 3. products

```sql id="db4"
product_id (PK)
product_name
category
price
```

👉 Managed by admin

---

### 🔹 4. orders

```sql id="db5"
order_id (PK)
customer_id (FK)
address_id (FK)
total_amount
order_date
```

---

### 🔹 5. order_items

```sql id="db6"
id (PK)
order_id (FK)
product_id (FK)
quantity
price
```

---

## 🔗 Relationships (Simplified)

```id="rel1"
Customer ────< Address
Customer ────< Orders
Order ────< OrderItems
Product ────< OrderItems
```

---

## 🔄 Complete Order Flow

```id="flow1"
Step 1: Create Customer
Step 2: Add Address
Step 3: Create Product (Admin)
Step 4: Place Order
Step 5: Fetch Order Details
```

---

## 🚀 API Endpoints

---

### 🔹 1. Create Customer

**POST** `/api/customers`

```json id="api1"
{
  "customerId": "CUST1001",
  "name": "Virendra Yadav",
  "email": "virendra.yadav@example.com",
  "phone": "9876543210"
}
```

---

### 🔹 2. Add Address

**POST** `/api/addresses`

```json id="api2"
{
  "customerId": "CUST1001",
  "street": "221B Baker Street",
  "city": "Mumbai",
  "state": "Maharashtra",
  "zipCode": "400001"
}
```

---

### 🔹 3. Create Product

**POST** `/api/products`

```json id="api3"
{
  "productId": "PROD101",
  "productName": "Wireless Mouse",
  "category": "Electronics",
  "price": 899.0
}
```

---

### 🔹 4. Place Order

**POST** `/api/orders`

```json id="api4"
{
  "customerId": "CUST1001",
  "addressId": 1,
  "items": [
    {
      "productId": "PROD101",
      "quantity": 2
    }
  ]
}
```

---

### 🔹 5. Get Order

**GET** `/api/orders/{orderId}`

---

## ⚙️ Business Logic Explained

---

### ✔ Order Placement Rules

Before placing an order:

* Customer must exist
* Address must belong to customer
* Product must exist

---

### ✔ Total Amount Calculation

```id="logic1"
Total Amount = Σ (Product Price × Quantity)
```

---

## 📂 Project Structure

```id="proj1"
com.example
│
├── entity        → Database models
├── dto           → Request/Response objects
├── repository    → JPA interfaces
├── service       → Business logic
├── controller    → REST APIs
```

---

## 🧪 Testing Flow (Step-by-Step)

```id="test1"
1. POST /customers
2. POST /addresses
3. POST /products
4. POST /orders
5. GET  /orders/{id}
```

---

## ⚠️ Known Limitations

* No authentication (JWT)
* No payment system
* No cart functionality
* N+1 query issue in order fetch

---

## 🚧 Future Enhancements

* 🔐 JWT Authentication
* 🛒 Cart system
* 💳 Payment integration
* 📦 Order status tracking
* ⚡ Query optimization

---

## 💡 Key Design Decision

> ❗ No JPA mapping (`@OneToMany`, etc.)

Instead:

* Use **Foreign Keys (IDs)**
* Handle relationships in **Service Layer**

---

## 🧠 Key Learning

> “Keep database simple, handle complexity in service layer.”

---

## 👨‍💻 Author

**Virendra Yadav**

---

## ⭐ Final Note

This project demonstrates a **real-world scalable backend design** approach used in many production systems.

---
