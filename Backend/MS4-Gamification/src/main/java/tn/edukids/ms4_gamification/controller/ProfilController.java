package tn.edukids.ms4_gamification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.service.ProfilService;
import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/api/profils") @RequiredArgsConstructor
public class ProfilController {
    private final ProfilService service;

    @GetMapping
    public List<ProfilResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ProfilResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<ProfilResponse> create(@Valid @RequestBody ProfilRequest request) {
        ProfilResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ProfilResponse update(@PathVariable Long id, @Valid @RequestBody ProfilRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
