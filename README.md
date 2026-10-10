# Food Order Management System 🍽️

A full-stack Food Order Management application featuring a Spring Boot REST API, PostgreSQL database, and a React (Vite) admin frontend.

---

## 🏗️ Project Architecture

```
Food/
├── food-order-management-frontend/   # React + Vite admin dashboard
│   ├── src/
│   │   ├── api/                      # Axios client configuration
│   │   ├── components/               # Reusable UI components
│   │   ├── config/                   # Entity configuration & routes
│   │   ├── pages/                    # Entity pages (Restaurants, Customers, Orders, etc.)
│   │   └── services/                 # API service functions
│   └── package.json
│
└── FoodOrderManagement/
    └── FoodOrderManagement/          # Spring Boot REST API
        ├── src/main/java/            # Controllers, Services, Entities, DTOs, Repositories
        ├── src/main/resources/       # application.properties
        └── pom.xml                   # Maven dependencies
```

---

## 🛠️ Tech Stack

- **Backend**: Java, Spring Boot, Spring Data JPA, Hibernate, Maven
- **Database**: PostgreSQL
- **Frontend**: React 18, Vite, React Router DOM, Axios, Lucide Icons

---

## 🚀 Getting Started

### Prerequisites

1. **Java Development Kit (JDK)**: JDK 17 or higher
2. **Node.js**: v18 or higher (with npm)
3. **PostgreSQL**: v14 or higher running locally on port `5432`

---

### 1. Database Setup

Create a PostgreSQL database named `Food`:
```sql
CREATE DATABASE "Food";
```

Update your database credentials in `FoodOrderManagement/FoodOrderManagement/src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Food
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

---

### 2. Backend Setup & Run

Navigate to the backend directory and launch the Spring Boot server:

```powershell
cd FoodOrderManagement/FoodOrderManagement
.\mvnw.cmd spring-boot:run
```
*(On Linux/macOS: `./mvnw spring-boot:run`)*

The API will start at: `http://localhost:8080`

---

### 3. Frontend Setup & Run

Navigate to the frontend directory:

```powershell
cd food-order-management-frontend
npm install
npm run dev
```

The frontend dashboard will be available at: `http://localhost:5173`

---

## 📡 API Modules Overview

- **Restaurants**: `/controller/restaurants` — CRUD operations for restaurant management.
- **Customers**: `/customer` — Manage customer accounts and order histories.
- **Menu Items**: `/item` — Manage menu items, pricing, and availability.
- **Orders**: `/orders` — Place orders, track statuses, and cancel orders.
- **Order Items**: `/orders/{id}/multiple-items` — Itemized order management.
- **Payments**: `/payment` — Manage payments, transactions, and payment methods.
