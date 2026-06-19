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

> More endpoints will be added as each module is implemented.
