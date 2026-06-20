package govtech_service_request_platform.service;

import govtech_service_request_platform.dto.StatusHistoryResponseDto;
import govtech_service_request_platform.exceptions.ResourceNotFoundException;
import govtech_service_request_platform.repository.ServiceRequestRepository;
import govtech_service_request_platform.repository.StatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusHistoryService {

    private final StatusHistoryRepository statusHistoryRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    public List<StatusHistoryResponseDto> getByServiceRequestId(Long serviceRequestId) {
        if (!serviceRequestRepository.existsById(serviceRequestId)) {
            throw new ResourceNotFoundException("Service request not found with id: " + serviceRequestId);
        }
        return statusHistoryRepository.findByServiceRequestIdOrderByChangedAtAsc(serviceRequestId).stream()
                .map(StatusHistoryResponseDto::fromEntity)
                .toList();
    }
}
