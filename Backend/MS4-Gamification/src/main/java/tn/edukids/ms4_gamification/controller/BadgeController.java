package tn.edukids.ms4_gamification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.service.BadgeService;
import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/api/badges") @RequiredArgsConstructor
public class BadgeController {
    private final BadgeService service;

    @GetMapping
    public List<BadgeResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public BadgeResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<BadgeResponse> create(@Valid @RequestBody BadgeRequest request) {
        BadgeResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public BadgeResponse update(@PathVariable Long id, @Valid @RequestBody BadgeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
