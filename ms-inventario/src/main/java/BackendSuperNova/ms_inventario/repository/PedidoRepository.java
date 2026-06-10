package BackendSuperNova.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.ms_inventario.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}