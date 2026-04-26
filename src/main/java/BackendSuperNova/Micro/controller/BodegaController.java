package BackendSuperNova.Micro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import BackendSuperNova.Micro.service.BodegaService;
import BackendSuperNova.Micro.model.Bodega;

@RestController
@RequestMapping("/api/v1/bodegas")
public class BodegaController {
    @Autowired
    private BodegaService bodegaService;

    @GetMapping
    public ResponseEntity<List<Bodega>> getAllBodega() {
        List<Bodega> bodegas = bodegaService.findAll();
        if (bodegas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bodegas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bodega> getBodegaById(@PathVariable Long id) {
        Bodega bodega = bodegaService.findById(id);
        if (bodega == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bodega);
    }

    @PostMapping
    public ResponseEntity<Bodega> createBodega(@RequestBody Bodega bodega) {
        Bodega createdBodega = bodegaService.save(bodega);
        return ResponseEntity.status(201).body(createdBodega);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bodega> updateBodega(@PathVariable Long id, @RequestBody Bodega bodega) {
        bodega.setId(id);
        Bodega updatedBodega = bodegaService.save(bodega);
        if (updatedBodega == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBodega);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodega(@PathVariable Long id) {
        bodegaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}