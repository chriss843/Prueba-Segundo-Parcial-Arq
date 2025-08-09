package edu.ec.com.central.controller;

import edu.ec.com.central.dto.CrearCosechaRequest;
import edu.ec.com.central.dto.EstadoUpdateRequest;
import edu.ec.com.central.messaging.EventPublisher;
import edu.ec.com.central.model.Agricultor;
import edu.ec.com.central.model.Cosecha;
import edu.ec.com.central.repository.AgricultorRepository;
import edu.ec.com.central.repository.CosechaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cosechas")
@RequiredArgsConstructor
public class CosechaController {

    private final AgricultorRepository agricultorRepo;
    private final CosechaRepository cosechaRepo;
    private final EventPublisher publisher;

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(cosechaRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearCosechaRequest req) {
        Agricultor ag = agricultorRepo.findById(req.agricultor_id())
                .orElseThrow(() -> new RuntimeException("Agricultor no existe"));
        Cosecha c = Cosecha.builder()
                .agricultor(ag)
                .producto(req.producto())
                .toneladas(req.toneladas())
                .estado("REGISTRADA")
                .build();
        c = cosechaRepo.save(c);
        publisher.publishNuevaCosecha(c.getCosechaId(), c.getProducto(), c.getToneladas());
        return ResponseEntity.ok(Map.of("cosecha_id", c.getCosechaId(), "estado", c.getEstado()));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable UUID id, @Valid @RequestBody EstadoUpdateRequest req) {
        return cosechaRepo.findById(id).map(c -> {
            c.setEstado(req.estado());
            if (req.factura_id() != null) c.setFacturaId(req.factura_id());
            cosechaRepo.save(c);
            return ResponseEntity.ok(Map.of("ok", true, "estado", c.getEstado(), "factura_id", c.getFacturaId()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!cosechaRepo.existsById(id)) return ResponseEntity.notFound().build();
        cosechaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
