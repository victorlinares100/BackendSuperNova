package BackendSuperNova.Micro.repository;

import BackendSuperNova.Micro.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}