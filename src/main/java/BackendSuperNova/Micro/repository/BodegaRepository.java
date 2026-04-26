package BackendSuperNova.Micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.Micro.model.Bodega;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    // Bodega findBySucursal(String sucursal);
}