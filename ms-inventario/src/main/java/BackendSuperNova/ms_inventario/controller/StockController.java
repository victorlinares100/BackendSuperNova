package BackendSuperNova.ms_inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import BackendSuperNova.ms_inventario.service.StockService;
import BackendSuperNova.ms_inventario.model.Stock;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping("/{id}/salida")
    public ResponseEntity<?> registrarSalida(
            @PathVariable Long id,
            @RequestParam Integer cantidad,
            @RequestParam String motivo) {
        try {
            Stock resultado = stockService.registrarSalida(id, cantidad, motivo);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
}

   @GetMapping
    public ResponseEntity<List<Stock>> getAllStock() {
        List<Stock> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);   // ← lista vacía = [] en vez de 204
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.findById(id);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stock);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.save(stock);
        return ResponseEntity.status(201).body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        stock.setId(id);
        Stock updatedStock = stockService.save(stock);
        if (updatedStock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}