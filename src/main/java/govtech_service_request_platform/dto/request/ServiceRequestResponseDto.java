package govtech_service_request_platform.dto.request;

import govtech_service_request_platform.entity.ServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ServiceRequestResponseDto {
    private Long id;
    private Long citizenId;
    private String citizenName;
    private String serviceType;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ServiceRequestResponseDto fromEntity(ServiceRequest serviceRequest) {
        return new ServiceRequestResponseDto(
                serviceRequest.getId(),
                serviceRequest.getCitizen().getId(),
                serviceRequest.getCitizen().getFullName(),
                serviceRequest.getServiceType(),
                serviceRequest.getDescription(),
                serviceRequest.getStatus().name(),
                serviceRequest.getCreatedAt(),
                serviceRequest.getUpdatedAt()
        );
    }
}
