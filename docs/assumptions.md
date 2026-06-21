# Assumptions

- File upload is not implemented; SupportingDocument stores metadata only (documentType, documentName, documentReference, verificationStatus).
- Citizen deletion is implemented as soft deactivation (status = INACTIVE), not physical deletion, to preserve historical service request data.
- Service request cancellation is implemented via status = CANCELLED rather than physical deletion, to preserve audit trail and status history.
- StatusHistory records previous and new status as strings to keep the audit trail decoupled from enum changes.
- JWT token expiry is set to 24 hours by default.
- Docker Compose is not provided — local setup instructions are documented in the main README.
- JWT secret is stored in application.properties for assessment purposes; production deployments should use environment variables or a secrets manager.
- Requests with no Authorization header return `403 Forbidden` rather than `401 Unauthorized`, due to Spring Security's default `ExceptionTranslationFilter` behavior when no custom `AuthenticationEntryPoint` is configured. A custom entry point could be added to strictly return 401 for missing credentials vs 403 for insufficient role, if time permitted.
- Self-registration as CITIZEN (`/api/v1/auth/register`) does not require or verify a matching record in the `citizens` table, nor does it create one. The `User` (login) and `Citizen` (profile) entities are intentionally independent. A more complete design would link `User.citizenId` to an existing `Citizen` record at registration time, rejecting registration if no matching ACTIVE citizen profile exists. This was a deliberate scope decision: the assessment's explicit requirement is role-based access control (which is implemented), not citizen-profile-linked authentication.