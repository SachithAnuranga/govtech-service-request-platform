package govtech_service_request_platform.dto.citizen;

import govtech_service_request_platform.entity.Citizen;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CitizenResponseDto {
    private Long id;
    private String fullName;
    private String nic;
    private String email;
    private String mobileNumber;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CitizenResponseDto fromEntity(Citizen citizen) {
        return new CitizenResponseDto(
                citizen.getId(),
                citizen.getFullName(),
                citizen.getNic(),
                citizen.getEmail(),
                citizen.getMobileNumber(),
                citizen.getAddress(),
                citizen.getStatus().name(),
                citizen.getCreatedAt(),
                citizen.getUpdatedAt()
        );
    }
}
