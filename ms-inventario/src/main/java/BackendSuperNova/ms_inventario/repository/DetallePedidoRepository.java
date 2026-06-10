package BackendSuperNova.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.ms_inventario.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}