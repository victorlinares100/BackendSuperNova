package BackendSuperNova.ms_inventario.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import BackendSuperNova.ms_inventario.model.DetallePedido;
import BackendSuperNova.ms_inventario.repository.DetallePedidoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> findAll() {
        return detallePedidoRepository.findAll();
    }

    @SuppressWarnings("null")
    public DetallePedido findById(Long id) {
        return detallePedidoRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public DetallePedido save(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public void delete(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}