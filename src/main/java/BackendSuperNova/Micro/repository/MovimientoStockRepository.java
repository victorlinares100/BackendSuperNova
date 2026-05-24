package BackendSuperNova.Micro.repository;

import BackendSuperNova.Micro.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {
}