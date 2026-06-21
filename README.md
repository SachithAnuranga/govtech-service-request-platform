# GovTech Sri Lanka — Digital Government Service Request Platform

A secure, production-ready backend system built with **Java Spring Boot** for managing digital government service requests between citizens and government service agents.

---

## Project Overview

This system enables citizens to:
- Register and manage profiles
- Submit government service requests
- Upload supporting document metadata
- Track request status in real time
- Receive notifications on status updates

It also enables government service agents and administrators to manage and process requests securely.

---

## Technical Stack

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL 8
- Maven
- JUnit 5 + Mockito
- Swagger / OpenAPI

---

## Architecture

The system follows a layered architecture:

- Controller Layer (REST APIs)
- Service Layer (Business Logic)
- Repository Layer (Data Access)
- Entity Layer (Database Models)

---

## Entities

- Citizen
- ServiceRequest
- SupportingDocument
- Notification
- StatusHistory
- User (Authentication & Roles)

| Entity | Purpose | Key Relationships |
|---|---|---|
| **User** | Authentication & role-based access | — |
| **Citizen** | Citizen profile data | Has many ServiceRequests |
| **ServiceRequest** | Government service request lifecycle | Belongs to Citizen; has many SupportingDocuments, Notifications, StatusHistory entries |
| **SupportingDocument** | Document metadata for a request | Belongs to ServiceRequest |
| **Notification** | Status-change alerts for citizens | Belongs to Citizen and ServiceRequest |
| **StatusHistory** | Audit trail of status changes | Belongs to ServiceRequest |

### Soft Delete Strategy
- **Citizen** → deactivated via `status = INACTIVE` (not physically deleted)
- **ServiceRequest** → cancelled via `status = CANCELLED` (not physically deleted)
- **SupportingDocument** → physically deleted (no historical dependency)
- **Notification** → physically deleted if removed (rare case)
---


---
## Security

Role-based access control is implemented using Spring Security + JWT.
Full details in [`docs/security.md`](docs/security.md)

### Roles
- **ADMIN** → Full access
- **SERVICE_AGENT** → Manage and process service requests
- **CITIZEN** → Submit and view own requests

### Authentication Flow
1. User logs in via `/api/v1/auth/login`
2. System returns a JWT token
3. Token must be included in all protected requests:
   ```
   Authorization: Bearer <token>
   ```
4. System validates token and enforces role-based access control

### Implementation Status
- ✅ JWT generation & validation (`JwtUtil`)
- ✅ Custom user authentication (`CustomUserDetailsService`)
- ✅ Request-level token validation (`JwtAuthenticationFilter`)
- ✅ Security rules & filter chain (`SecurityConfig`)
- ⏳ Auth controller (`/login`, `/register`) — in progress

---

## End-to-End Business Flow

1. Administrator creates a Citizen profile
2. Citizen submits a Service Request
3. System validates citizen before request creation
4. Supporting document metadata is added
5. Service Agent reviews request
6. Service Agent updates status:
   - SUBMITTED → IN_REVIEW → APPROVED / REJECTED
7. System generates notification on status change
8. Citizen views notifications
9. Citizen marks notification as read
10. Agent/Admin views full request + history

---

## 📡 API Structure

- `/api/v1/auth` → Authentication
- `/api/v1/citizens` → Citizen management
- `/api/v1/service-requests` → Service requests
- `/api/v1/documents` → Supporting documents
- `/api/v1/notifications` → Notifications

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### Setup Steps

**1. Clone the repository**
```bash
git clone https://github.com/SachithAnuranga/govtech-service-request-platform.git
cd govtech-service-request-platform
```

**2. Create the database**
```sql
CREATE DATABASE govtech_db;
```

**3. Configure database credentials**

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/govtech_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Access the application**
```
Base URL : http://localhost:8080
Swagger  : http://localhost:8080/swagger-ui.html
```

---

## Error Response Format

All errors follow a consistent structure:

```json
{
  "timestamp": "2026-06-19T10:00:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Citizen not found with id: 1"
}
```

### HTTP Status Codes Used

| Code | Meaning |
|---|---|
| 200 | Success |
| 201 | Resource created |
| 400 | Validation error |
| 401 | Unauthorized (missing/invalid token) |
| 403 | Forbidden (role not permitted) |
| 404 | Resource not found |
| 500 | Internal server error |

---

## API Documentation

Full API documentation is available in [`docs/api-documentation.md`](docs/api-documentation.md)

---

## Postman Collection

The Postman collection is available at [`postman/govtech-collection.json`](postman/govtech-collection.json)

Import into Postman and configure these collection variables:
- `base_url` — your server URL (default: `http://localhost:8080`)
- `token` — JWT from logging in as ADMIN
- `citizen_token` — JWT from logging in as CITIZEN
- `agent_token` — JWT from logging in as SERVICE_AGENT

Run Authentication → login first to obtain tokens before testing other endpoints.

### Postman Testing Flow

1. Call `/auth/login` to get a JWT token
2. Set the token in Postman's Authorization tab (Bearer Token)
3. Execute APIs in this order:
   - Create Citizen
   - Create Service Request
   - Add Supporting Document
   - Update Request Status
   - View Notifications
   - Mark Notification as Read
4. Test error scenarios (invalid ID, unauthorized access)

---

## Testing

```bash
mvn test
```

Details in [`docs/testing.md`](docs/testing.md)

---

## Assumptions

- File upload is not implemented; only document metadata is stored
- Soft delete is used for critical entities
- JWT token expiry is 24 hours
- Email/SMS notifications are not integrated; only database notifications
- Docker Compose is not provided — local setup instructions are documented above

Full details in [`docs/assumptions.md`](docs/assumptions.md)

---

### Implementation Status
- ✅ JWT generation & validation (`JwtUtil`)
- ✅ Custom user authentication (`CustomUserDetailsService`)
- ✅ Request-level token validation (`JwtAuthenticationFilter`)
- ✅ Security rules & filter chain (`SecurityConfig`)
- ✅ Auth controller (`/register`, `/login`)

## Author

Sachith Anuranga
Senior Software Engineer Assessment — GovTech Sri Lanka
