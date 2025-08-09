package edu.ec.com.central.controller;

import edu.ec.com.central.model.Agricultor;
import edu.ec.com.central.repository.AgricultorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/agricultores")
@RequiredArgsConstructor
public class AgricultorController {

    private final AgricultorRepository repo;

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Agricultor body) {
        body.setAgricultorId(null);
        return ResponseEntity.ok(repo.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody Agricultor body) {
        return repo.findById(id).map(a -> {
            a.setNombre(body.getNombre());
            a.setFinca(body.getFinca());
            a.setUbicacion(body.getUbicacion());
            a.setCorreo(body.getCorreo());
            return ResponseEntity.ok(repo.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
