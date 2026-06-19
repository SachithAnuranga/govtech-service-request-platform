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

> More endpoints will be added as each module is implemented.
