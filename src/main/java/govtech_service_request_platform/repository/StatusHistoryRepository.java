package govtech_service_request_platform.repository;


import govtech_service_request_platform.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    List<StatusHistory> findByServiceRequestIdOrderByChangedAtAsc(Long serviceRequestId);
}
