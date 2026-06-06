package BackendSuperNova.Micro.controller;

import BackendSuperNova.Micro.model.Solicitud;
import BackendSuperNova.Micro.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping
    public ResponseEntity<List<Solicitud>> getAll() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @PostMapping
    public ResponseEntity<Solicitud> create(@RequestBody Solicitud solicitud) {
        return ResponseEntity.status(201).body(solicitudService.save(solicitud));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> update(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        solicitud.setId(id);
        return ResponseEntity.ok(solicitudService.save(solicitud));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solicitudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}