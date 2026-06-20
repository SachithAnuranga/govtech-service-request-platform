package govtech_service_request_platform.service;

import govtech_service_request_platform.dto.document.DocumentRequestDto;
import govtech_service_request_platform.dto.document.DocumentResponseDto;
import govtech_service_request_platform.dto.document.DocumentUpdateRequestDto;
import govtech_service_request_platform.entity.ServiceRequest;
import govtech_service_request_platform.entity.SupportingDocument;
import govtech_service_request_platform.enums.VerificationStatus;
import govtech_service_request_platform.exceptions.ResourceNotFoundException;
import govtech_service_request_platform.repository.ServiceRequestRepository;
import govtech_service_request_platform.repository.SupportingDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportingDocumentService {

    private final SupportingDocumentRepository documentRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    @Transactional
    public DocumentResponseDto addDocument(DocumentRequestDto request) {
        ServiceRequest sr = serviceRequestRepository.findById(request.getServiceRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + request.getServiceRequestId()));

        SupportingDocument doc = SupportingDocument.builder()
                .serviceRequest(sr)
                .documentType(request.getDocumentType())
                .documentName(request.getDocumentName())
                .documentReference(request.getDocumentReference())
                .verificationStatus(VerificationStatus.PENDING)
                .build();

        return DocumentResponseDto.fromEntity(documentRepository.save(doc));
    }

    public DocumentResponseDto getById(Long id) {
        SupportingDocument doc = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        return DocumentResponseDto.fromEntity(doc);
    }

    public List<DocumentResponseDto> getByServiceRequestId(Long serviceRequestId) {
        return documentRepository.findByServiceRequestId(serviceRequestId).stream()
                .map(DocumentResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public DocumentResponseDto updateDocument(Long id, DocumentUpdateRequestDto request) {
        SupportingDocument doc = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));

        doc.setDocumentType(request.getDocumentType());
        doc.setDocumentName(request.getDocumentName());
        doc.setDocumentReference(request.getDocumentReference());

        if (request.getVerificationStatus() != null) {
            doc.setVerificationStatus(VerificationStatus.valueOf(request.getVerificationStatus().toUpperCase()));
        }

        return DocumentResponseDto.fromEntity(documentRepository.save(doc));
    }

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }
}
