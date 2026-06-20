package govtech_service_request_platform;

import govtech_service_request_platform.dto.citizen.CitizenRequestDto;
import govtech_service_request_platform.dto.citizen.CitizenResponseDto;
import govtech_service_request_platform.entity.Citizen;
import govtech_service_request_platform.enums.CitizenStatus;
import govtech_service_request_platform.exceptions.DuplicateResourceException;
import govtech_service_request_platform.exceptions.ResourceNotFoundException;
import govtech_service_request_platform.repository.CitizenRepository;
import govtech_service_request_platform.service.CitizenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitizenServiceTest {

    @Mock
    private CitizenRepository citizenRepository;

    @InjectMocks
    private CitizenService citizenService;

    private CitizenRequestDto request;
    private Citizen citizen;

    @BeforeEach
    void setUp() {
        request = new CitizenRequestDto();
        request.setFullName("Sachith Anuranga");
        request.setNic("922219090v");
        request.setEmail("sachith@govetech.com");
        request.setMobileNumber("0771234567");
        request.setAddress("Colombo 03");

        citizen = Citizen.builder()
                .id(1L)
                .fullName("Sachith Anuranga")
                .nic("922219090v")
                .email("sachith@govetech.com")
                .mobileNumber("0771234567")
                .address("Colombo 03")
                .status(CitizenStatus.ACTIVE)
                .build();
    }

    @Test
    void createCitizen_Success() {
        when(citizenRepository.existsByNic(request.getNic())).thenReturn(false);
        when(citizenRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(citizenRepository.save(any(Citizen.class))).thenReturn(citizen);

        CitizenResponseDto response = citizenService.createCitizen(request);

        assertNotNull(response);
        assertEquals("Sachith Anuranga", response.getFullName());
        assertEquals("ACTIVE", response.getStatus());
        verify(citizenRepository, times(1)).save(any(Citizen.class));
    }

    @Test
    void createCitizen_DuplicateNic_ThrowsException() {
        when(citizenRepository.existsByNic(request.getNic())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> citizenService.createCitizen(request));
        verify(citizenRepository, never()).save(any(Citizen.class));
    }

    @Test
    void createCitizen_DuplicateEmail_ThrowsException() {
        when(citizenRepository.existsByNic(request.getNic())).thenReturn(false);
        when(citizenRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> citizenService.createCitizen(request));
        verify(citizenRepository, never()).save(any(Citizen.class));
    }

    @Test
    void getCitizenById_Success() {
        when(citizenRepository.findById(1L)).thenReturn(Optional.of(citizen));

        CitizenResponseDto response = citizenService.getCitizenById(1L);

        assertEquals("Sachith Anuranga", response.getFullName());
    }

    @Test
    void getCitizenById_NotFound_ThrowsException() {
        when(citizenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> citizenService.getCitizenById(99L));
    }

    @Test
    void deactivateCitizen_Success() {
        when(citizenRepository.findById(1L)).thenReturn(Optional.of(citizen));
        when(citizenRepository.save(any(Citizen.class))).thenReturn(citizen);

        citizenService.deactivateCitizen(1L);

        assertEquals(CitizenStatus.INACTIVE, citizen.getStatus());
        verify(citizenRepository, times(1)).save(citizen);
    }

    @Test
    void deactivateCitizen_NotFound_ThrowsException() {
        when(citizenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> citizenService.deactivateCitizen(99L));
    }
}
