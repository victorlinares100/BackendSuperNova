package BackendSuperNova.ms_inventario.repository;

import BackendSuperNova.ms_inventario.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByProductoIdAndBodegaId(Long productoId, Long bodegaId);
}