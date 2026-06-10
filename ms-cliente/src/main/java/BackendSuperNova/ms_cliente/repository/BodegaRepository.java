package BackendSuperNova.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.ms_cliente.model.Bodega;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    // Bodega findBySucursal(String sucursal);
}