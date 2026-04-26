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

import BackendSuperNova.Micro.service.DetallePedidoService;
import BackendSuperNova.Micro.model.DetallePedido;

@RestController
@RequestMapping("/api/v1/detallespedidos")
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedido>> getAllDetallePedido() {
        List<DetallePedido> detalles = detallePedidoService.findAll();
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> getDetallePedidoById(@PathVariable Long id) {
        DetallePedido detalle = detallePedidoService.findById(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle);
    }

    @PostMapping
    public ResponseEntity<DetallePedido> createDetallePedido(@RequestBody DetallePedido detallePedido) {
        DetallePedido createdDetalle = detallePedidoService.save(detallePedido);
        return ResponseEntity.status(201).body(createdDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> updateDetallePedido(@PathVariable Long id, @RequestBody DetallePedido detallePedido) {
        detallePedido.setId(id);
        DetallePedido updatedDetalle = detallePedidoService.save(detallePedido);
        if (updatedDetalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDetalle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable Long id) {
        detallePedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}