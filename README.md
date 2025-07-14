# Expense Tracker – User Guide

Ez a dokumentum bemutatja az Expense Tracker REST API végpontjainak használatát. Az alkalmazás JWT-alapú stateless hitelesítést használ.

---

## Tartalomjegyzék

1. [Bevezető](#bevezető)
2. [Authentikációs végpontok](#1-authentikációs-végpontok)
    - [Regisztráció](#11-regisztráció)
    - [Bejelentkezés](#12-bejelentkezés)
3. [JWT Token használata](#2-jwt-token-használata)
4. [Védett végpontok](#3-védett-végpontok)
    - [Transaction végpontok](#31-transaction-végpontok)
    - [Felhasználó végpontok](#32-felhasználó-végpontok)
5. [Engedélyezési szabályok](#4-engedélyezési-szabályok)
6. [cURL példák](#5-curl-példák)
7. [Megjegyzések](#6-megjegyzések)

---

## Bevezető

- Alap URL: `http://localhost:8080`
- Minden védett végpont JWT tokent igényel.
- A token a login után szerezhető be.

---

## 1. Authentikációs végpontok

### 1.1 Regisztráció

- **URL**: `/api/auth/register`
- **Módszer**: `POST`
- **Leírás**: Új felhasználó regisztrálása.
- **Request Body példa**:

```json
{
  "username": "myuser",
  "password": "mypassword",
  "email": "myuser@example.com"
}
```
 - Válasz példa:

```json
{
  "id": 1,
  "username": "myuser",
  "email": "myuser@example.com"
}
```

### 1.2 Bejelentkezés

- **URL**: `/api/auth/login`
- **Módszer**: `POST`
- **Leírás**: Bejelentkezés és JWT token igénylése.
- **Request Body példa**
```json
{
  "username": "myuser",
  "password": "mypassword"
}
```

- Válasz példa:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```

## 2. JWT Token használata

A védett végpontoknál a következő HTTP header kötelező:

```
Authorization: Bearer <JWT_TOKEN>
```

## 3. Védett végpontok
Az alábbi végpontok érvényes JWT tokent igényelnek.

### 3.1 Transaction végpontok

`GET /api/transaction`

- Összes transaction lekérdezése.
- **Header:** ``Authorization: Bearer <JWT_TOKEN>``
- Válasz példa:

```json
[
  {
    "id": 1,
    "amount": 5000,
    "description": "Salary",
    "date": "2025-06-01"
  }
]
```

