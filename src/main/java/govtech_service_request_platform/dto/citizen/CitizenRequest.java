package govtech_service_request_platform.dto.citizen;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CitizenRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String nic;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String address;
}