package BackendSuperNova.Micro.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import BackendSuperNova.Micro.model.MovimientoStock;
import BackendSuperNova.Micro.repository.MovimientoStockRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovimientoStockService {
    @Autowired
    private MovimientoStockRepository movimientoStockRepository;

    public List<MovimientoStock> findAll() {
        return movimientoStockRepository.findAll();
    }

    @SuppressWarnings("null")
    public MovimientoStock findById(Long id) {
        return movimientoStockRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public MovimientoStock save(MovimientoStock movimientoStock) {
        return movimientoStockRepository.save(movimientoStock);
    }

    public void delete(Long id) {
        movimientoStockRepository.deleteById(id);
    }
}