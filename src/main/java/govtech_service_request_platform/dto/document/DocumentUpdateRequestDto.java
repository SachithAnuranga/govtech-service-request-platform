package govtech_service_request_platform.dto.document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocumentUpdateRequestDto {
    @NotBlank
    private String documentType;

    @NotBlank
    private String documentName;

    @NotBlank
    private String documentReference;

    private String verificationStatus; // optional, PENDING/VERIFIED/REJECTED
}
