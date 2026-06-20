package govtech_service_request_platform.controller;

import govtech_service_request_platform.dto.request.ServiceRequestDto;
import govtech_service_request_platform.dto.request.ServiceRequestResponseDto;
import govtech_service_request_platform.dto.request.ServiceRequestUpdateDto;
import govtech_service_request_platform.dto.request.StatusUpdateRequestDto;
import govtech_service_request_platform.service.ServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @PostMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ServiceRequestResponseDto> create(@Valid @RequestBody ServiceRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceRequestService.createRequest(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT', 'CITIZEN')")
    public ResponseEntity<ServiceRequestResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRequestService.getById(id));
    }

    @GetMapping("/citizen/{citizenId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT', 'CITIZEN')")
    public ResponseEntity<List<ServiceRequestResponseDto>> getByCitizen(@PathVariable Long citizenId) {
        return ResponseEntity.ok(serviceRequestService.getByCitizenId(citizenId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<List<ServiceRequestResponseDto>> getAll() {
        return ResponseEntity.ok(serviceRequestService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<ServiceRequestResponseDto> update(@PathVariable Long id, @Valid @RequestBody ServiceRequestUpdateDto request) {
        return ResponseEntity.ok(serviceRequestService.updateRequest(id, request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<ServiceRequestResponseDto> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequestDto request) {
        return ResponseEntity.ok(serviceRequestService.updateStatus(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        serviceRequestService.cancelRequest(id);
        return ResponseEntity.ok("Service request cancelled successfully");
    }
}