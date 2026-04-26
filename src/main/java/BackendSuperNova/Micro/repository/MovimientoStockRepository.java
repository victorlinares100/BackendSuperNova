package BackendSuperNova.Micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.Micro.model.MovimientoStock;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {
}