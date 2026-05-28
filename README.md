# PerfectFit

<div align="center">

### Fitness Center Management System

A full-stack platform for managing fitness center operations, subscriptions, schedules, and client interactions.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-Framework-6DB33F?style=for-the-badge&logo=springboot)
![SQL Server](https://img.shields.io/badge/SQL_Server-Database-CC2927?style=for-the-badge&logo=microsoftsqlserver)
![Thymeleaf](https://img.shields.io/badge/Frontend-Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf)
![Apache Tomcat](https://img.shields.io/badge/Server-Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat)

</div>

---

## Overview

PerfectFit is a web-based fitness center management system designed to simplify daily gym operations for both administrators and clients. The platform provides tools for subscription management, schedule handling, course participation, and attendance tracking.

The project combines a modern Java backend with a responsive frontend and a relational database architecture to create a scalable and maintainable solution.

<img width="1275" height="716" alt="image" src="https://github.com/user-attachments/assets/25fb69ea-d571-4652-a338-a87c76399467" />

---

## Features

### User Management
- Secure login and logout system
- Account management
- Password and email updates
- Role-based interactions

### Subscription System

<img width="1280" height="641" alt="image" src="https://github.com/user-attachments/assets/b58bb3f9-0035-4327-a692-a157df695e3d" />

- Purchase subscriptions
- Manage subscription plans
- Track active subscriptions
- Configure course limits and deadlines

### Course & Schedule Management

<img width="1280" height="633" alt="image" src="https://github.com/user-attachments/assets/ce350b0c-9e56-47d2-8033-cb2c2b7e2342" />

- Browse available courses
- Create and manage schedules
- Track course participation
- Organize training sessions by weekday and time

### Attendance Tracking
<img width="903" height="575" alt="image" src="https://github.com/user-attachments/assets/b5252e47-2798-4dd6-abc8-1d679e10c887" />

- Inform about absence
- Store absence records for both coaches and clients
- Monitor participation history

### Fitness Center Administration

<img width="1280" height="628" alt="image" src="https://github.com/user-attachments/assets/99751932-2722-434c-8a25-1de8eab4253f" />

- Coach management
- Client management
- Course pricing configuration
- Centralized operational workflow

---

## System Architecture

The system is structured as a classic multi-layered web application:

```text
Frontend (React)
        ↓
REST API (Spring Boot)
        ↓
Business Logic Layer
        ↓
Spring Data JPA
        ↓
Microsoft SQL Server
```

---

## Tech Stack

| Layer | Technologies |
|---|---|
| Backend | Java, Spring Boot, Spring Data JPA, Spring Security |
| Frontend | Thymleaf |
| Server | Apache Tomcat |
| Database | Microsoft SQL Server |
| ORM | JPA / Hibernate |

---

## Database Model

<img width="1303" height="739" alt="image" src="https://github.com/user-attachments/assets/de619023-961b-4e0b-8613-f23bb59fa25c" />

The project database includes entities related to:

- Clients
- Coaches
- Courses
- Schedules
- Subscriptions
- Course sales
- Subscription sales
- Attendance and absence records

The schema is designed to support flexible course scheduling and subscription management workflows.

---

## Use Cases

<img width="960" height="386" alt="Use case diagram Fitness Center drawio" src="https://github.com/user-attachments/assets/7cf70032-0bc9-4315-9eaa-2709a440038c" />


### Client Actions
- Buy a subscription
- Join fitness courses
- Update account information
- Report absence

### Administrative Actions
- Create schedules
- Manage subscriptions
- Configure courses
- Monitor attendance

---

## Getting Started

### Prerequisites

Make sure you have installed:

- Java 17+
- Node.js & npm
- Microsoft SQL Server
- Maven

---

##Setup

```bash
git clone https://github.com/vadimkatsel/perfect-fit.git
cd perfect-fit
```

Configure your database connection inside:

```properties
application.properties
```

Run:

```bash
mvn spring-boot:run
```
---


## Security

The project uses Spring Security for:

- Authentication
- Authorization
- Protected endpoints
- Secure account management

---

## Future Improvements

- Online payments
- Mobile application support
- Real-time notifications
- Analytics dashboard
- Trainer performance statistics
- QR attendance system

---

## License

This project is created for educational and portfolio purposes.

---

