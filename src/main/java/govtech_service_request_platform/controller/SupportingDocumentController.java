package govtech_service_request_platform.controller;

import govtech_service_request_platform.dto.document.DocumentRequestDto;
import govtech_service_request_platform.dto.document.DocumentResponseDto;
import govtech_service_request_platform.dto.document.DocumentUpdateRequestDto;
import govtech_service_request_platform.service.SupportingDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class SupportingDocumentController {

    private final SupportingDocumentService documentService;

    @PostMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<DocumentResponseDto> addDocument(@Valid @RequestBody DocumentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.addDocument(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT', 'CITIZEN')")
    public ResponseEntity<DocumentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getById(id));
    }

    @GetMapping("/service-request/{serviceRequestId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT', 'CITIZEN')")
    public ResponseEntity<List<DocumentResponseDto>> getByServiceRequest(@PathVariable Long serviceRequestId) {
        return ResponseEntity.ok(documentService.getByServiceRequestId(serviceRequestId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<DocumentResponseDto> update(@PathVariable Long id, @Valid @RequestBody DocumentUpdateRequestDto request) {
        return ResponseEntity.ok(documentService.updateDocument(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }
}