# Security

## Authentication Approach

JWT (JSON Web Token) based stateless authentication using the `jjwt` library, implemented with Spring Security.

- Passwords are hashed using **BCrypt** (`BCryptPasswordEncoder`)
- On successful login, a JWT is issued containing the username (subject) and role (custom claim)
- Token must be sent in the `Authorization: Bearer <token>` header for all protected endpoints
- Token expiry: **24 hours** (`jwt.expiration=86400000` ms)

## Flow

1. User logs in via `POST /api/v1/auth/login` with username + password
2. Credentials are verified against the `users` table (password hashed with BCrypt)
3. On success, a JWT is generated containing the username and role
4. The client includes the token on all subsequent requests via the `Authorization: Bearer <token>` header
5. `JwtAuthenticationFilter` intercepts every request, validates the token, and sets the authenticated user in Spring Security's context
6. Role-based access is enforced using Spring Security authorities (`ROLE_ADMIN`, `ROLE_SERVICE_AGENT`, `ROLE_CITIZEN`)

## Components

| Component | Responsibility |
|---|---|
| `JwtUtil` | Generates and validates JWT tokens |
| `CustomUserDetailsService` | Loads user + role from database for authentication |
| `JwtAuthenticationFilter` | Intercepts requests, validates token, sets security context |
| `SecurityConfig` | Defines password encoding, URL access rules, filter chain |

## Roles & Access

| Role | Access |
|---|---|
| `ADMIN` | Full platform access — citizen management, document deletion, request cancellation |
| `SERVICE_AGENT` | Process service requests, review/verify supporting documents, update request status |
| `CITIZEN` | Submit own service requests, view own requests/documents/notifications |

## Token Structure

```json
{
  "sub": "username",
  "role": "CITIZEN",
  "iat": 1718800000,
  "exp": 1718886400
}
```

## Known Limitations

- JWT secret is currently stored in `application.properties` in plain text for assessment purposes. In a production environment, this should be loaded from an environment variable or a secrets manager (e.g. AWS Secrets Manager, Vault).
- No token refresh mechanism is implemented — tokens simply expire after 24 hours and the user must log in again.
- No token blacklist/revocation on logout — logout is handled client-side by discarding the token.
- No password reset / forgot-password flow implemented — out of scope for this assessment.

## URL-Level Access Control

| Endpoint Pattern | Access |
|---|---|
| `/api/v1/auth/**` | Public (login) |
| `/api/v1/citizens/**` | ADMIN, SERVICE_AGENT only |
| `/api/v1/service-requests/**` | Authenticated (fine-grained via `@PreAuthorize` at method level) |
| `/api/v1/documents/**` | Authenticated (fine-grained via `@PreAuthorize` at method level) |
| `/api/v1/notifications/**` | Authenticated (fine-grained via `@PreAuthorize` at method level) |

Method-level `@PreAuthorize` annotations enforce ownership rules not expressible at the URL level — e.g. a CITIZEN may only view their own service requests, not another citizen's.

## Securing Sensitive Data

- Passwords are never returned in API responses (excluded at DTO level)
- BCrypt hashing means raw passwords are never stored or recoverable
- JWT payload contains only username and role — no sensitive personal data (NIC, email, etc.)

## Known Behavior Note
Requests with no Authorization header currently return `403 Forbidden` rather than `401 Unauthorized`, due to Spring Security's default `ExceptionTranslationFilter` behavior when no custom `AuthenticationEntryPoint` is configured. Both responses correctly indicate denied access; a custom entry point could be added to strictly return 401 for missing credentials vs 403 for insufficient role, if required.
