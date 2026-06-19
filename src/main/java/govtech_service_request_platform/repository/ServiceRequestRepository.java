package govtech_service_request_platform.repository;

import govtech_service_request_platform.entity.Citizen;
import govtech_service_request_platform.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByCitizen(Citizen citizen);
    List<ServiceRequest> findByCitizenId(Long citizenId);
    List<ServiceRequest> findByStatus(ServiceRequest status);
    List<ServiceRequest> findByServiceType(String serviceType);
}