package BackendSuperNova.Micro.service;

import BackendSuperNova.Micro.model.Venta;
import BackendSuperNova.Micro.model.Stock;
import BackendSuperNova.Micro.model.DetalleVenta;
import BackendSuperNova.Micro.repository.VentaRepository;
import BackendSuperNova.Micro.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private StockRepository stockRepository;

    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        // 1. Calcular total y enlazar detalles
        double total = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            detalle.setVenta(venta);
            total += detalle.getCantidad() * detalle.getPrecioUnitario();

            // 2. Descontar stock — busca el stock del producto en esa bodega
            List<Stock> stocks = stockRepository
                .findByProductoIdAndBodegaId(
                    detalle.getProducto().getId(),
                    venta.getBodega().getId()
                );

            if (stocks.isEmpty()) {
                throw new RuntimeException(
                    "Sin stock para producto ID " + detalle.getProducto().getId()
                );
            }

            Stock stock = stocks.get(0);
            int nueva = stock.getCantidadDisponible() - detalle.getCantidad();
            if (nueva < 0) {
                throw new RuntimeException(
                    "Stock insuficiente para: " + detalle.getProducto().getId()
                );
            }
            stock.setCantidadDisponible(nueva);
            stockRepository.save(stock);
        }

        venta.setTotal(total);
        return ventaRepository.save(venta);
    }
}