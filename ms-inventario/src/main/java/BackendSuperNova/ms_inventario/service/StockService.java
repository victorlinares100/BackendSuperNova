package BackendSuperNova.ms_inventario.service;

import BackendSuperNova.ms_inventario.model.Stock;
import BackendSuperNova.ms_inventario.model.MovimientoStock;
import BackendSuperNova.ms_inventario.repository.StockRepository;
import BackendSuperNova.ms_inventario.repository.MovimientoStockRepository;
import BackendSuperNova.ms_inventario.repository.ProductoRepository; 
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
    @Autowired private ProductoRepository productoRepository; 

    public List<Stock> findAll() { 
    
        return stockRepository.findAll(); 
    }
    
    public Stock findById(Long id) { 
        return stockRepository.findById(id).orElse(null); 
    }

    public Stock save(Stock stock) {
        Stock guardado = stockRepository.save(stock);
        Stock cargado  = stockRepository.findById(guardado.getId()).orElse(guardado);

        // Buscamos el nombre real del producto en la BD local para evitar el "null"
        String nombreProducto = "—";
        if (cargado.getProducto() != null && cargado.getProducto().getId() != null) {
            nombreProducto = productoRepository.findById(cargado.getProducto().getId())
                                .map(p -> p.getNombre())
                                .orElse("—");
        }

        MovimientoStock mov = new MovimientoStock();
        mov.setStock(cargado);
        mov.setCantidad(cargado.getCantidadDisponible());
        mov.setFechaMovimiento(LocalDate.now());
        mov.setTipoMovimiento("ENTRADA");
        mov.setDescripcion("Ingreso de " + cargado.getCantidadDisponible()
            + " unidad(es) de " + nombreProducto 
            + " en Bodega ID: " + cargado.getBodegaId());
        movimientoStockRepository.save(mov);

        return guardado; 
    }

    public void delete(Long id) { stockRepository.deleteById(id); }

    public Stock registrarSalida(Long stockId, Integer cantidad, String motivo) {
        Stock stock = stockRepository.findById(stockId)
            .orElseThrow(() -> new RuntimeException("Stock no encontrado"));

        if (stock.getCantidadDisponible() < cantidad) {
            throw new RuntimeException("Stock insuficiente para registrar la salida");
        }

        stock.setCantidadDisponible(stock.getCantidadDisponible() - cantidad);
        Stock guardado = stockRepository.save(stock);

        String nombreProducto = "—";
        if (stock.getProducto() != null && stock.getProducto().getId() != null) {
            nombreProducto = productoRepository.findById(stock.getProducto().getId())
                .map(p -> p.getNombre()).orElse("—");
        }

        MovimientoStock mov = new MovimientoStock();
        mov.setStock(guardado);
        mov.setCantidad(cantidad);
        mov.setFechaMovimiento(LocalDate.now());
        mov.setTipoMovimiento("SALIDA");
        mov.setDescripcion(motivo + " — " + cantidad + " unidad(es) de "
            + nombreProducto + " en Bodega ID: " + guardado.getBodegaId());
        movimientoStockRepository.save(mov);

        return guardado;
    }
}