package BackendSuperNova.ms_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BackendSuperNova.ms_inventario.model.Categoria;
import BackendSuperNova.ms_inventario.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @SuppressWarnings("null")
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }
     public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    
}


