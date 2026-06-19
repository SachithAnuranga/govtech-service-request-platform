package govtech_service_request_platform.controller;

import govtech_service_request_platform.dto.citizen.CitizenRequest;
import govtech_service_request_platform.dto.citizen.CitizenResponse;
import govtech_service_request_platform.service.CitizenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citizens")
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CitizenResponse> createCitizen(@Valid @RequestBody CitizenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citizenService.createCitizen(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<CitizenResponse> getCitizen(@PathVariable Long id) {
        return ResponseEntity.ok(citizenService.getCitizenById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE_AGENT')")
    public ResponseEntity<List<CitizenResponse>> getAllCitizens() {
        return ResponseEntity.ok(citizenService.getAllCitizens());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CitizenResponse> updateCitizen(@PathVariable Long id, @Valid @RequestBody CitizenRequest request) {
        return ResponseEntity.ok(citizenService.updateCitizen(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivateCitizen(@PathVariable Long id) {
        citizenService.deactivateCitizen(id);
        return ResponseEntity.ok("Citizen deactivated successfully");
    }
}
