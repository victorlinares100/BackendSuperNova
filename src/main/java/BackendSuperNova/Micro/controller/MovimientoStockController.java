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
import BackendSuperNova.Micro.service.MovimientoStockService;
import BackendSuperNova.Micro.model.MovimientoStock;

@RestController
@RequestMapping("/api/v1/movimientosStock")
public class MovimientoStockController {
    @Autowired
    private MovimientoStockService movimientoStockService;

    @GetMapping
    public ResponseEntity<List<MovimientoStock>> getAllMovimientoStock() {
        List<MovimientoStock> movimientos = movimientoStockService.findAll();
        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStock> getMovimientoStockById(@PathVariable Long id) {
        MovimientoStock movimiento = movimientoStockService.findById(id);
        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping
    public ResponseEntity<MovimientoStock> createMovimientoStock(@RequestBody MovimientoStock movimientoStock) {
        MovimientoStock createdMovimiento = movimientoStockService.save(movimientoStock);
        return ResponseEntity.status(201).body(createdMovimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoStock> updateMovimientoStock(@PathVariable Long id, @RequestBody MovimientoStock movimientoStock) {
        movimientoStock.setId(id);
        MovimientoStock updatedMovimiento = movimientoStockService.save(movimientoStock);
        if (updatedMovimiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMovimiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimientoStock(@PathVariable Long id) {
        movimientoStockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}