package BackendSuperNova.ms_inventario.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import BackendSuperNova.ms_inventario.model.Pedido;
import BackendSuperNova.ms_inventario.repository.PedidoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @SuppressWarnings("null")
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }
}