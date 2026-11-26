package tn.edukids.ms4_gamification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.service.ParticipationService;
import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/api/participations") @RequiredArgsConstructor
public class ParticipationController {
    private final ParticipationService service;

    @GetMapping
    public List<ParticipationResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ParticipationResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<ParticipationResponse> create(@Valid @RequestBody ParticipationRequest request) {
        ParticipationResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ParticipationResponse update(@PathVariable Long id, @Valid @RequestBody ParticipationRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
