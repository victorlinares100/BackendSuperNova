package BackendSuperNova.ms_inventario;

import BackendSuperNova.ms_inventario.model.*;
import BackendSuperNova.ms_inventario.repository.*;
import BackendSuperNova.ms_inventario.service.VentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock private VentaRepository           ventaRepository;
    @Mock private StockRepository           stockRepository;
    @Mock private MovimientoStockRepository movimientoStockRepository;

    @InjectMocks private VentaService ventaService;

    @Test
    void registrarVenta_cuandoHayStock_descuentaCantidadCorrectamente() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Leche Entera 1L");

        // En ms-inventario Stock usa bodegaId (Long) en vez de objeto Bodega
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setProducto(producto);
        stock.setBodegaId(1L);      // ← Long en vez de objeto Bodega
        stock.setCantidadDisponible(50);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setBodegaId(1L);    // ← Long en vez de objeto Bodega
        detalle.setCantidad(10);
        detalle.setPrecioUnitario(990.0);

        Venta venta = new Venta();
        venta.setDetalles(List.of(detalle));

        when(stockRepository.findByProductoIdAndBodegaId(1L, 1L))
            .thenReturn(List.of(stock));
        when(stockRepository.save(any(Stock.class)))
            .thenAnswer(inv -> inv.getArgument(0));
        when(ventaRepository.save(any(Venta.class)))
            .thenAnswer(inv -> inv.getArgument(0));

        Venta resultado = ventaService.registrarVenta(venta);

        assertEquals(40, stock.getCantidadDisponible());
        assertEquals(9900.0, resultado.getTotal());
        verify(movimientoStockRepository, times(1)).save(any(MovimientoStock.class));
    }

    @Test
    void registrarVenta_cuandoStockInsuficiente_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setId(2L);
        producto.setNombre("Queso Gauda 250g");

        Stock stock = new Stock();
        stock.setProducto(producto);
        stock.setBodegaId(1L);
        stock.setCantidadDisponible(5);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setBodegaId(1L);
        detalle.setCantidad(20);
        detalle.setPrecioUnitario(3490.0);

        Venta venta = new Venta();
        venta.setDetalles(List.of(detalle));

        when(stockRepository.findByProductoIdAndBodegaId(2L, 1L))
            .thenReturn(List.of(stock));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ventaService.registrarVenta(venta));

        assertTrue(ex.getMessage().contains("Stock insuficiente"));
        verify(ventaRepository, never()).save(any());
        verify(movimientoStockRepository, never()).save(any());
    }
}