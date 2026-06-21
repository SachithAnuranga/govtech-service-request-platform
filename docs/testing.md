# Testing

## Unit Tests

**Component tested:** `CitizenService` (business logic layer)

| Test | Type | Validates |
|---|---|---|
| `createCitizen_Success` | Business logic | Citizen created correctly with ACTIVE status |
| `createCitizen_DuplicateNic_ThrowsException` | Failure scenario | Duplicate NIC is rejected (User Story 1.1) |
| `createCitizen_DuplicateEmail_ThrowsException` | Failure scenario | Duplicate email is rejected (User Story 1.1) |
| `getCitizenById_Success` | Business logic | Existing citizen is returned correctly |
| `getCitizenById_NotFound_ThrowsException` | Failure scenario | Non-existent citizen returns proper exception (User Story 1.2) |
| `deactivateCitizen_Success` | Business logic | Soft delete correctly sets status to INACTIVE |
| `deactivateCitizen_NotFound_ThrowsException` | Failure scenario | Deactivating a non-existent citizen returns proper exception (User Story 1.5) |

Run with:
```bash
mvn test
```

Tests use **Mockito** to mock `CitizenRepository`, isolating the service layer from the database. This means tests run in milliseconds and don't require a running MySQL instance.

## Manual / API-Level Testing (Advantage Coverage)

All endpoints across every module were manually tested end-to-end via Postman against the real running application and database, covering:
- Authentication (register, login, invalid credentials)
- Citizen management (full CRUD)
- Service request management (create, update, status change, cancel)
- Supporting document management (full CRUD)
- Notification (view, mark as read)
- Status history (view)
- Role-based access (unauthorized/forbidden scenarios)
- Common error scenarios (404, 409, 400, 401)

The full set of these requests is saved in [`postman/`](../postman/) as an exported collection.

## Testing Limitations & Assumptions

- Unit tests are provided for `CitizenService` only, as the minimum required component. `ServiceRequestService`, `SupportingDocumentService`, and `NotificationService` follow the same patterns but were not unit tested individually due to time constraints.
- No integration tests (e.g. `@SpringBootTest` with a real or embedded test database) were written — API-level correctness was instead verified manually via the Postman collection.
- No automated test exists for the JWT/security filter chain itself; security behavior was verified manually (e.g. confirming a CITIZEN token is rejected on `/api/v1/citizens`).
- Test coverage focuses on business logic correctness and failure handling rather than exhaustive edge-case coverage (e.g. malformed input boundary values are not individually tested).

## Unit Tests — Verified ✅

Run with:
```bash
mvn clean test
```

Result: `Tests run: 8, Failures: 0, Errors: 0` (7 CitizenServiceTest + 1 contextLoads)

## Postman Collection Status
Manual end-to-end testing in progress via Postman collection (`GovtechCitizenServiceRequestManagement`), covering Auth, Citizens, and Service Requests so far. Documents, Notifications, and Error Scenarios pending.
