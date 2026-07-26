# Java ATM Management System

A console-based ATM Management System developed using Java and
Object-Oriented Programming concepts.

## Features

- Account number and PIN authentication
- Three login attempts
- Balance enquiry
- Cash deposit
- Cash withdrawal
- Fund transfer
- Mini statement
- PIN change
- Input validation

## Technologies

- Java 17+
- OOP
- Java Collections
- BigDecimal
- Exception handling

## Project Structure

```text
Java-ATM-System/
├── src/
│   ├── Main.java
│   ├── Account.java
│   ├── ATMService.java
│   └── Transaction.java
├── README.md
└── .gitignore
```

## Demo Accounts

| Account Number | PIN  | Balance |
|----------------|------|---------|
| 1001           | 1234 | 10000   |
| 1002           | 5678 | 15000   |

## How to Run

Compile the application:

```bash
javac -d out src/*.java
```

Run the application:

```bash
java -cp out Main
```

## Future Improvements

- MySQL database integration
- JDBC implementation
- Password/PIN hashing
- Admin dashboard
- Swing or JavaFX user interface
- Transaction receipt generation
- Unit testing with JUnit

## Disclaimer

This application is created for educational purposes only.
Do not use real banking credentials.