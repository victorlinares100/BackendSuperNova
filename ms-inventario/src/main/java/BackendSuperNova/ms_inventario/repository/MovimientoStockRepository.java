package BackendSuperNova.ms_inventario.repository;

import BackendSuperNova.ms_inventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {
}