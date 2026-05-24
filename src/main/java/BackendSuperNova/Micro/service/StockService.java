package BackendSuperNova.Micro.service;

import BackendSuperNova.Micro.model.Stock;
import BackendSuperNova.Micro.model.MovimientoStock;
import BackendSuperNova.Micro.repository.StockRepository;
import BackendSuperNova.Micro.repository.MovimientoStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StockService {

    @Autowired private StockRepository stockRepository;
    @Autowired private MovimientoStockRepository movimientoStockRepository;

    public List<Stock> findAll() { return stockRepository.findAll(); }
    public Stock findById(Long id) { return stockRepository.findById(id).orElse(null); }

    public Stock save(Stock stock) {
    Stock guardado = stockRepository.save(stock);
    Stock cargado  = stockRepository.findById(guardado.getId()).orElse(guardado); // ← falta esta

    MovimientoStock mov = new MovimientoStock();
    mov.setStock(cargado);
    mov.setCantidad(cargado.getCantidadDisponible());
    mov.setFechaMovimiento(LocalDate.now());
    mov.setTipoMovimiento("ENTRADA");
    mov.setDescripcion("Ingreso de " + cargado.getCantidadDisponible()
        + " unidad(es) de "
        + (cargado.getProducto() != null ? cargado.getProducto().getNombre() : "—")
        + " en "
        + (cargado.getBodega() != null ? cargado.getBodega().getSucursal() : "—"));
    movimientoStockRepository.save(mov);

    return guardado;
}

    public void delete(Long id) { stockRepository.deleteById(id); }
}