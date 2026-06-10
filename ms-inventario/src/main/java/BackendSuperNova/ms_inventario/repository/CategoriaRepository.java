package BackendSuperNova.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.ms_inventario.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    //Categoria findByNombreCategoria(String nombre);
}
