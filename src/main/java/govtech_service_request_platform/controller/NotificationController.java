package govtech_service_request_platform.controller;

import govtech_service_request_platform.dto.notification.NotificationResponseDto;
import govtech_service_request_platform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/citizen/{citizenId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT', 'CITIZEN')")
    public ResponseEntity<List<NotificationResponseDto>> getByCitizen(@PathVariable Long citizenId) {
        return ResponseEntity.ok(notificationService.getByCitizenId(citizenId));
    }

    @PatchMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT', 'CITIZEN')")
    public ResponseEntity<NotificationResponseDto> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
}