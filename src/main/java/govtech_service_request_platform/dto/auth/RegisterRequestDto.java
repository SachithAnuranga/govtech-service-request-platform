package govtech_service_request_platform.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String role; // ADMIN, SERVICE_AGENT, CITIZEN
}
