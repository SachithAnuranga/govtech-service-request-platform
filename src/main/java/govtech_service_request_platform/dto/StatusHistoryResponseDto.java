package govtech_service_request_platform.dto;

import govtech_service_request_platform.entity.StatusHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatusHistoryResponseDto {
    private Long id;
    private Long serviceRequestId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;

    public static StatusHistoryResponseDto fromEntity(StatusHistory statusHistory) {
        return new StatusHistoryResponseDto(
                statusHistory.getId(),
                statusHistory.getServiceRequest().getId(),
                statusHistory.getPreviousStatus(),
                statusHistory.getNewStatus(),
                statusHistory.getChangedAt()
        );
    }
}
