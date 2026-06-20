package govtech_service_request_platform.service;

import govtech_service_request_platform.dto.request.ServiceRequestDto;
import govtech_service_request_platform.dto.request.ServiceRequestResponseDto;
import govtech_service_request_platform.dto.request.ServiceRequestUpdateDto;
import govtech_service_request_platform.dto.request.StatusUpdateRequestDto;
import govtech_service_request_platform.entity.Citizen;
import govtech_service_request_platform.entity.Notification;
import govtech_service_request_platform.entity.ServiceRequest;
import govtech_service_request_platform.entity.StatusHistory;
import govtech_service_request_platform.enums.NotificationStatus;
import govtech_service_request_platform.enums.RequestStatus;
import govtech_service_request_platform.exceptions.ResourceNotFoundException;
import govtech_service_request_platform.repository.CitizenRepository;
import govtech_service_request_platform.repository.NotificationRepository;
import govtech_service_request_platform.repository.ServiceRequestRepository;
import govtech_service_request_platform.repository.StatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final CitizenRepository citizenRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public ServiceRequestResponseDto createRequest(ServiceRequestDto request) {
        Citizen citizen = citizenRepository.findById(request.getCitizenId())
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + request.getCitizenId()));

        ServiceRequest sr = ServiceRequest.builder()
                .citizen(citizen)
                .serviceType(request.getServiceType())
                .description(request.getDescription())
                .status(RequestStatus.SUBMITTED)
                .build();

        return ServiceRequestResponseDto.fromEntity(serviceRequestRepository.save(sr));
    }

    public ServiceRequestResponseDto getById(Long id) {
        ServiceRequest sr = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));
        return ServiceRequestResponseDto.fromEntity(sr);
    }

    public List<ServiceRequestResponseDto> getByCitizenId(Long citizenId) {
        return serviceRequestRepository.findByCitizenId(citizenId).stream()
                .map(ServiceRequestResponseDto::fromEntity)
                .toList();
    }

    public List<ServiceRequestResponseDto> getAll() {
        return serviceRequestRepository.findAll().stream()
                .map(ServiceRequestResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public ServiceRequestResponseDto updateRequest(Long id, ServiceRequestUpdateDto request) {
        ServiceRequest sr = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

        sr.setServiceType(request.getServiceType());
        sr.setDescription(request.getDescription());

        return ServiceRequestResponseDto.fromEntity(serviceRequestRepository.save(sr));
    }

    @Transactional
    public ServiceRequestResponseDto updateStatus(Long id, StatusUpdateRequestDto request) {
        ServiceRequest sr = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

        String oldStatus = sr.getStatus().name();
        RequestStatus newStatus;
        try {
            newStatus = RequestStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
        }

        sr.setStatus(newStatus);
        serviceRequestRepository.save(sr);

        // Record status history
        StatusHistory history = StatusHistory.builder()
                .serviceRequest(sr)
                .previousStatus(oldStatus)
                .newStatus(newStatus.name())
                .build();
        statusHistoryRepository.save(history);

        // Create notification
        Notification notification = Notification.builder()
                .citizen(sr.getCitizen())
                .serviceRequest(sr)
                .message("Your service request #" + sr.getId() + " status changed to " + newStatus.name())
                .status(NotificationStatus.UNREAD)
                .build();
        notificationRepository.save(notification);

        return ServiceRequestResponseDto.fromEntity(sr);
    }

    @Transactional
    public void cancelRequest(Long id) {
        ServiceRequest sr = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));
        sr.setStatus(RequestStatus.CANCELLED);
        serviceRequestRepository.save(sr);
    }
}
