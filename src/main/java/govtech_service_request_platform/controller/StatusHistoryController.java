package govtech_service_request_platform.controller;

import govtech_service_request_platform.dto.StatusHistoryResponseDto;
import govtech_service_request_platform.service.StatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-requests")
@RequiredArgsConstructor
public class StatusHistoryController {

    private final StatusHistoryService statusHistoryService;

    @GetMapping("/{id}/history")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<List<StatusHistoryResponseDto>> getHistory(@PathVariable("id") Long serviceRequestId) {
        return ResponseEntity.ok(statusHistoryService.getByServiceRequestId(serviceRequestId));
    }
}
