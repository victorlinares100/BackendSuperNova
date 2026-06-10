package BackendSuperNova.ms_inventario.repository;

import BackendSuperNova.ms_inventario.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}