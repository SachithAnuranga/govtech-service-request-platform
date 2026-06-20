package govtech_service_request_platform.dto.notification;

import govtech_service_request_platform.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private Long citizenId;
    private Long serviceRequestId;
    private String message;
    private String status;
    private LocalDateTime createdAt;

    public static NotificationResponseDto fromEntity(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getCitizen().getId(),
                notification.getServiceRequest().getId(),
                notification.getMessage(),
                notification.getStatus().name(),
                notification.getCreatedAt()
        );
    }
}
