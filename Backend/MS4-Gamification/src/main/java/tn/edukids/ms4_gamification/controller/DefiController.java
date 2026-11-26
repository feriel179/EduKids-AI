package tn.edukids.ms4_gamification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.service.DefiService;
import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/api/defis") @RequiredArgsConstructor
public class DefiController {
    private final DefiService service;

    @GetMapping
    public List<DefiResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public DefiResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<DefiResponse> create(@Valid @RequestBody DefiRequest request) {
        DefiResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public DefiResponse update(@PathVariable Long id, @Valid @RequestBody DefiRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
