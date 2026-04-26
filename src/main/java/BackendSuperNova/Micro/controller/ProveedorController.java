package BackendSuperNova.Micro.controller;

import BackendSuperNova.Micro.model.Proveedor;
import BackendSuperNova.Micro.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.obtenerPorId(id);
        
        if (proveedor.isPresent()) {
            return ResponseEntity.ok(proveedor.get()); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping
    public Proveedor crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.guardarProveedor(proveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build(); 
    }
}
