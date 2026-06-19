package govtech_service_request_platform.repository;


import govtech_service_request_platform.entity.Notification;
import govtech_service_request_platform.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCitizenId(Long citizenId);
    List<Notification> findByCitizenIdAndStatus(Long citizenId, NotificationStatus status);
}
