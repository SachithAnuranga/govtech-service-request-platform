package govtech_service_request_platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceRequestUpdateDto {

    @NotBlank
    private String serviceType;

    @NotBlank
    private String description;

}
