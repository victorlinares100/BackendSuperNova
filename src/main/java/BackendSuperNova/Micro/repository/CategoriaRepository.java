package BackendSuperNova.Micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import BackendSuperNova.Micro.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    //Categoria findByNombreCategoria(String nombre);
}
