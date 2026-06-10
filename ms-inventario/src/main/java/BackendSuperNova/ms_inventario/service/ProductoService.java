package BackendSuperNova.ms_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BackendSuperNova.ms_inventario.model.Producto;
import BackendSuperNova.ms_inventario.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @SuppressWarnings("null")
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
    
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}