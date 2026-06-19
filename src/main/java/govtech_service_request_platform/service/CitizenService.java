package govtech_service_request_platform.service;

import govtech_service_request_platform.dto.citizen.CitizenRequest;
import govtech_service_request_platform.dto.citizen.CitizenResponse;
import govtech_service_request_platform.entity.Citizen;
import govtech_service_request_platform.enums.CitizenStatus;
import govtech_service_request_platform.exceptions.DuplicateResourceException;
import govtech_service_request_platform.exceptions.ResourceNotFoundException;
import govtech_service_request_platform.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenRepository citizenRepository;

    @Transactional
    public CitizenResponse createCitizen(CitizenRequest request) {
        if (citizenRepository.existsByNic(request.getNic())) {
            throw new DuplicateResourceException("Citizen already exists with NIC: " + request.getNic());
        }
        if (citizenRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Citizen already exists with email: " + request.getEmail());
        }

        Citizen citizen = Citizen.builder()
                .fullName(request.getFullName())
                .nic(request.getNic())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .address(request.getAddress())
                .status(CitizenStatus.ACTIVE)
                .build();

        return CitizenResponse.fromEntity(citizenRepository.save(citizen));
    }

    public CitizenResponse getCitizenById(Long id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));
        return CitizenResponse.fromEntity(citizen);
    }

    public List<CitizenResponse> getAllCitizens() {
        return citizenRepository.findAll().stream()
                .map(CitizenResponse::fromEntity)
                .toList();
    }

    @Transactional
    public CitizenResponse updateCitizen(Long id, CitizenRequest request) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));

        citizen.setFullName(request.getFullName());
        citizen.setNic(request.getNic());
        citizen.setEmail(request.getEmail());
        citizen.setMobileNumber(request.getMobileNumber());
        citizen.setAddress(request.getAddress());

        return CitizenResponse.fromEntity(citizenRepository.save(citizen));
    }

    @Transactional
    public void deactivateCitizen(Long id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));
        citizen.setStatus(CitizenStatus.INACTIVE);
        citizenRepository.save(citizen);
    }
}
