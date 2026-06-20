package govtech_service_request_platform.dto.document;

import govtech_service_request_platform.entity.SupportingDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentResponseDto {
    private Long id;
    private Long serviceRequestId;
    private String documentType;
    private String documentName;
    private String documentReference;
    private String verificationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DocumentResponseDto fromEntity(SupportingDocument document) {
        return new DocumentResponseDto(
                document.getId(),
                document.getServiceRequest().getId(),
                document.getDocumentType(),
                document.getDocumentName(),
                document.getDocumentReference(),
                document.getVerificationStatus().name(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
