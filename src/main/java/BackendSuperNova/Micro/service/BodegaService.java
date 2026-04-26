package BackendSuperNova.Micro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BackendSuperNova.Micro.model.Bodega;
import BackendSuperNova.Micro.repository.BodegaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BodegaService {
    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Bodega> findAll() {
        return bodegaRepository.findAll();
    }

    @SuppressWarnings("null")
    public Bodega findById(Long id) {
        return bodegaRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Bodega save(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    public void delete(Long id) {
        bodegaRepository.deleteById(id);
    }
    
    public void deleteById(Long id) {
        bodegaRepository.deleteById(id);
    }
}