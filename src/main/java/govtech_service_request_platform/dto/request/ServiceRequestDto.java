package govtech_service_request_platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceRequestDto {
    @NotNull
    private Long citizenId;

    @NotBlank
    private String serviceType;

    @NotBlank
    private String description;
}
