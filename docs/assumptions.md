# Assumptions

- File upload is not implemented; SupportingDocument stores metadata only (documentType, documentName, documentReference, verificationStatus).
- Citizen deletion is implemented as soft deactivation (status = INACTIVE), not physical deletion, to preserve historical service request data.
- Service request cancellation is implemented via status = CANCELLED rather than physical deletion, to preserve audit trail and status history.
- StatusHistory records previous and new status as strings to keep the audit trail decoupled from enum changes.
- JWT token expiry is set to 24 hours by default.
- Docker Compose is not provided — local setup instructions are documented in the main README.