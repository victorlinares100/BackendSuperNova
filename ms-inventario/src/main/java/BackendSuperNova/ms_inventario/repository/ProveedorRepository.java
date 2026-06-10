package BackendSuperNova.ms_inventario.repository;

import BackendSuperNova.ms_inventario.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
  
}