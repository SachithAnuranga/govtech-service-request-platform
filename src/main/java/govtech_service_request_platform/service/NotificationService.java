package govtech_service_request_platform.service;

import govtech_service_request_platform.dto.notification.NotificationResponseDto;
import govtech_service_request_platform.entity.Notification;
import govtech_service_request_platform.enums.NotificationStatus;
import govtech_service_request_platform.exceptions.ResourceNotFoundException;
import govtech_service_request_platform.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationResponseDto> getByCitizenId(Long citizenId) {
        return notificationRepository.findByCitizenId(citizenId).stream()
                .map(NotificationResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public NotificationResponseDto markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setStatus(NotificationStatus.READ);
        return NotificationResponseDto.fromEntity(notificationRepository.save(notification));
    }
}
