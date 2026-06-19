package govtech_service_request_platform.repository;

import govtech_service_request_platform.entity.SupportingDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportingDocumentRepository extends JpaRepository<SupportingDocument, Long> {
    List<SupportingDocument> findByServiceRequestId(Long serviceRequestId);
}