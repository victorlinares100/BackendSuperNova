package BackendSuperNova.Micro.service;

import BackendSuperNova.Micro.model.Stock;
import BackendSuperNova.Micro.model.Venta;
import BackendSuperNova.Micro.model.DetalleVenta;
import BackendSuperNova.Micro.model.MovimientoStock;
import BackendSuperNova.Micro.repository.VentaRepository;
import BackendSuperNova.Micro.repository.StockRepository;
import BackendSuperNova.Micro.repository.MovimientoStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private StockRepository stockRepository;
    @Autowired private MovimientoStockRepository movimientoStockRepository;

    public List<Venta> findAll() { return ventaRepository.findAll(); }
    public Venta findById(Long id) { return ventaRepository.findById(id).orElse(null); }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        double total = 0;

        for (DetalleVenta detalle : venta.getDetalles()) {
            detalle.setVenta(venta);
            total += detalle.getCantidad() * detalle.getPrecioUnitario();

            // 1. Descontar stock (AHORA BUSCA LA BODEGA DESDE EL DETALLE)
            List<Stock> stocks = stockRepository.findByProductoIdAndBodegaId(
                detalle.getProducto().getId(), 
                detalle.getBodega().getId() // <-- CAMBIO AQUÍ
            );
            
            if (stocks.isEmpty()) throw new RuntimeException(
                "Sin stock para producto ID " + detalle.getProducto().getId() + " en la bodega seleccionada."
            );

            Stock stock = stocks.get(0);
            int nueva = stock.getCantidadDisponible() - detalle.getCantidad();
            
            if (nueva < 0) throw new RuntimeException(
                "Stock insuficiente para producto ID " + detalle.getProducto().getId()
            );
            
            stock.setCantidadDisponible(nueva);
            stockRepository.save(stock);

            // 2. Registrar movimiento de VENTA
            MovimientoStock mov = new MovimientoStock();
            mov.setStock(stock);
            mov.setCantidad(detalle.getCantidad());
            mov.setFechaMovimiento(LocalDate.now());
            mov.setTipoMovimiento("VENTA");
            mov.setDescripcion("Venta de " + detalle.getCantidad()
                + " unidad(es) de " + stock.getProducto().getNombre() 
                + " desde " + stock.getBodega().getSucursal()); // <-- Pequeña mejora en descripción
            movimientoStockRepository.save(mov);
        }

        venta.setTotal(total);
        return ventaRepository.save(venta);
    }
}