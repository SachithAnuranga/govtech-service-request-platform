package govtech_service_request_platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusUpdateRequestDto {
    @NotBlank
    private String status; // SUBMITTED, IN_REVIEW, APPROVED, REJECTED, CANCELLED
}
