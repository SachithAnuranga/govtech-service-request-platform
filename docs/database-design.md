# Database Design

## Entity Relationship Overview

User (independent — authentication only)

Citizen 1 ──── * ServiceRequest

│

├── 1 ──── * SupportingDocument

├── 1 ──── * StatusHistory

└── 1 ──── * Notification (also linked to Citizen)

## Entities

### User
| Field | Type | Notes |
|---|---|---|
| id | Long | PK |
| username | String | unique |
| password | String | hashed |
| role | Enum (Roles) | ADMIN, SERVICE_AGENT, CITIZEN |

### Citizen
| Field | Type | Notes |
|---|---|---|
| id | Long | PK |
| fullName | String | required |
| nic | String | unique |
| email | String | unique |
| mobileNumber | String | required |
| address | String | required |
| status | Enum | ACTIVE, INACTIVE |
| createdAt / updatedAt | DateTime | auto-managed |

### ServiceRequest
| Field | Type | Notes |
|---|---|---|
| id | Long | PK |
| citizen_id | FK → Citizen | required |
| serviceType | String | required |
| description | String | required |
| status | Enum | SUBMITTED, IN_REVIEW, APPROVED, REJECTED, CANCELLED |
| createdAt / updatedAt | DateTime | auto-managed |

### SupportingDocument
| Field | Type | Notes |
|---|---|---|
| id | Long | PK |
| service_request_id | FK → ServiceRequest | required |
| documentType | String | required |
| documentName | String | required |
| documentReference | String | metadata only, no file upload |
| verificationStatus | Enum | PENDING, VERIFIED, REJECTED |
| createdAt / updatedAt | DateTime | auto-managed |

### Notification
| Field | Type | Notes |
|---|---|---|
| id | Long | PK |
| citizen_id | FK → Citizen | required |
| service_request_id | FK → ServiceRequest | required |
| message | String | required |
| status | Enum | UNREAD, READ |
| createdAt | DateTime | auto-managed |

### StatusHistory
| Field | Type | Notes |
|---|---|---|
| id | Long | PK |
| service_request_id | FK → ServiceRequest | required |
| previousStatus | String | snapshot at time of change |
| newStatus | String | snapshot at time of change |
| changedAt | DateTime | auto-managed |

## Design Decisions & Assumptions

- **Soft delete** used for Citizen (`INACTIVE`) and ServiceRequest (`CANCELLED`) to preserve historical data and audit trail.
- **StatusHistory** stores status as String rather than enum reference, decoupling history from future enum changes.
- **Notification** links to both Citizen and ServiceRequest directly for simpler querying, even though ServiceRequest already links to Citizen.
- **File upload** is out of scope — SupportingDocument stores metadata only.
- **Timestamps** (`createdAt`, `updatedAt`) are managed automatically via JPA lifecycle callbacks (`@PrePersist`, `@PreUpdate`).
