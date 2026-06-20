# API Documentation

## Authentication

### Register
`POST /api/v1/auth/register`
```json
{
  "username": "admin1",
  "password": "Password123",
  "role": "ADMIN"
}
```
Response: `200 OK` — "User registered successfully"

### Login
`POST /api/v1/auth/login`
```json
{
  "username": "admin1",
  "password": "Password123"
}
```
Response: `200 OK`
```json
{
  "token": "eyJhbGciOi...",
  "username": "admin1",
  "role": "ADMIN"
}
```
## Error Response Format

All errors follow a consistent structure:

```json
{
  "timestamp": "2026-06-20T22:00:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Citizen not found with id: 1"
}
```

### Handled Error Cases
| Exception | HTTP Status |
|---|---|
| ResourceNotFoundException | 404 |
| DuplicateResourceException | 409 |
| BadCredentialsException | 401 |
| Validation errors | 400 |
| Unexpected errors | 500 |

## Citizen Management

All endpoints require `Authorization: Bearer <token>`.

### Create Citizen
`POST /api/v1/citizens` — ADMIN only
```json
{
  "fullName": "John Doe",
  "nic": "199912345678",
  "email": "john@example.com",
  "mobileNumber": "0771234567",
  "address": "123 Main St, Colombo"
}
```
Response: `201 Created`

### Get Citizen by ID
`GET /api/v1/citizens/{id}` — ADMIN, SERVICE_AGENT
Response: `200 OK` or `404 Not Found`

### List All Citizens
`GET /api/v1/citizens` — ADMIN, SERVICE_AGENT
Response: `200 OK` (array)

### Update Citizen
`PUT /api/v1/citizens/{id}` — ADMIN only
Same body as Create. Response: `200 OK` or `404 Not Found`

### Deactivate Citizen
`DELETE /api/v1/citizens/{id}` — ADMIN only
Soft delete — sets status to INACTIVE. Response: `200 OK` or `404 Not Found`

## Service Request Management

All endpoints require `Authorization: Bearer <token>`.

### Create Service Request
`POST /api/v1/service-requests` — CITIZEN only
```json
{
  "citizenId": 1,
  "serviceType": "Birth Certificate",
  "description": "Requesting a certified copy of birth certificate"
}
```
Response: `201 Created` — initial status is `SUBMITTED`

### Get Service Request by ID
`GET /api/v1/service-requests/{id}` — ADMIN, SERVICE_AGENT, CITIZEN
Response: `200 OK` or `404 Not Found`

### Get Requests by Citizen
`GET /api/v1/service-requests/citizen/{citizenId}` — ADMIN, SERVICE_AGENT, CITIZEN
Response: `200 OK` (array, empty array if none)

### List All Service Requests
`GET /api/v1/service-requests` — ADMIN, SERVICE_AGENT
Response: `200 OK` (array)

### Update Service Request Details
`PUT /api/v1/service-requests/{id}` — ADMIN, SERVICE_AGENT
```json
{
  "serviceType": "Birth Certificate",
  "description": "Updated description"
}
```
Response: `200 OK` or `404 Not Found`

### Update Service Request Status
`PATCH /api/v1/service-requests/{id}/status` — ADMIN, SERVICE_AGENT
```json
{
  "status": "IN_REVIEW"
}
```
Valid values: `SUBMITTED`, `IN_REVIEW`, `APPROVED`, `REJECTED`, `CANCELLED`
Response: `200 OK`
Side effects: creates a `StatusHistory` record and an unread `Notification` for the citizen automatically.

### Cancel Service Request
`DELETE /api/v1/service-requests/{id}` — ADMIN only
Soft delete — sets status to `CANCELLED`. Response: `200 OK` or `404 Not Found`

> More endpoints will be added as each module is implemented.
