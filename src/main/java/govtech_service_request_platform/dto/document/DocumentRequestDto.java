package govtech_service_request_platform.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentRequestDto {
    @NotNull
    private Long serviceRequestId;

    @NotBlank
    private String documentType;

    @NotBlank
    private String documentName;

    @NotBlank
    private String documentReference;
}
