package govtech_service_request_platform.repository;

import govtech_service_request_platform.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByNic(String nic);
    boolean existsByNic(String nic);
    boolean existsByEmail(String email);
}