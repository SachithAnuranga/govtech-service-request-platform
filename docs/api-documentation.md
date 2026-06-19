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

> More endpoints will be added as each module is implemented.
