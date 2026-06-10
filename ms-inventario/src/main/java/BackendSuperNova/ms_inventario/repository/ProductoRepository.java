package BackendSuperNova.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.ms_inventario.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Producto findByCodigoDeBarras(String codigoDeBarras);
}