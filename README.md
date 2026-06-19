# GovTech Sri Lanka — Digital Government Service Request Platform

A secure, production-aware **Java Spring Boot** backend system for managing 
digital government service requests.

---

## 📋 Table of Contents
- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Security & Roles](#security--roles)
- [Database Design](#database-design)
- [Postman Collection](#postman-collection)
- [Testing](#testing)
- [Assumptions & Limitations](#assumptions--limitations)
- [Design Decisions](#design-decisions)

---

## 📌 Project Overview

This system enables citizens to submit and track government service requests 
digitally. It supports citizen management, service request lifecycle management, 
supporting document handling, and real-time notifications.

**Key Capabilities:**
- Citizen profile management
- Service request submission and tracking
- Supporting document management
- Notification and status tracking
- Role-based access control

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT |
| Database | MySQL 8.x |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |
| API Docs | Swagger / OpenAPI |
| Testing | JUnit 5, Mockito |

---

## 🏗️ System Architecture

> Details available in [`docs/database-design.md`](docs/database-design.md)

**Core Modules:**
1. Citizen Management
2. Service Request Management
3. Supporting Document Management
4. Notification & Status Tracking

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- MySQL 8.x

### Local Setup Steps

**1. Clone the repository**
```bash
git clone https://github.com/SachithAnuranga/govtech-service-request-platform.git
cd govtech-service-request-platform
```

**2. Create the database**
```sql
CREATE DATABASE govtech_db;
```

**3. Configure application properties**

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/govtech_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Access the application**

Base URL : http://localhost:8080
Swagger  : http://localhost:8080/swagger-ui.html

---

## 📡 API Documentation

Full API documentation is available in [`docs/api-documentation.md`](docs/api-documentation.md)

| Module | Base Path |
|---|---|
| Authentication | `/api/v1/auth` |
| Citizens | `/api/v1/citizens` |
| Service Requests | `/api/v1/service-requests` |
| Documents | `/api/v1/documents` |
| Notifications | `/api/v1/notifications` |

---

## 🔐 Security & Roles

Full security documentation in [`docs/security.md`](docs/security.md)

| Role | Access Level |
|---|---|
| `ADMIN` | Full platform access |
| `SERVICE_AGENT` | Process requests, review documents |
| `CITIZEN` | Submit requests, view own data |

### Test Credentials
> Will be updated after implementation

---

## 🗄️ Database Design

Full database design in [`docs/database-design.md`](docs/database-design.md)

**Core Entities:**
- `citizens`
- `service_requests`
- `supporting_documents`
- `notifications`
- `status_history`
- `users`

---

## 📮 Postman Collection

The Postman collection is available in the [`postman/`](postman/) folder.

**Import Steps:**
1. Open Postman
2. Click **Import**
3. Select `postman/govtech-platform-collection.json`
4. Set environment variables
5. Run authentication first before other requests

**Collection Covers:**
- ✅ Authentication
- ✅ Citizen management
- ✅ Service request management
- ✅ Document management
- ✅ Notifications
- ✅ End-to-end business flow
- ✅ Error scenarios

---

## 🧪 Testing

Full testing details in [`docs/testing.md`](docs/testing.md)

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ServiceRequestServiceTest
```

---

## 📝 Assumptions & Limitations

Full details in [`docs/assumptions.md`](docs/assumptions.md)

- Actual file upload is not implemented — document metadata only
- Docker Compose is not provided — local setup instructions above
- JWT token expiry is set to 24 hours by default

---

## 🏛️ Design Decisions

- **Soft delete** used for citizens and service requests to preserve data history
- **JWT** selected for stateless authentication suitable for REST APIs
- **Status history** maintained as a separate table for full audit trail
- **Notification** auto-generated on every service request status change

---

## 👤 Author

> Sachith Anuranga 
> Senior Software Engineer Assessment  
> GovTech Sri Lanka — Digital Government Service Request Platform
