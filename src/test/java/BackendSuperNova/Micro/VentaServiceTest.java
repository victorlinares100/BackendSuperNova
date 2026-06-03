package BackendSuperNova.Micro;

import BackendSuperNova.Micro.model.*;
import BackendSuperNova.Micro.repository.VentaRepository;
import BackendSuperNova.Micro.repository.StockRepository;
import BackendSuperNova.Micro.repository.MovimientoStockRepository;
import BackendSuperNova.Micro.service.VentaService;
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
    // PRUEBA 1: Venta exitosa — el stock debe bajar correctamente
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Leche Entera 1L");

        Bodega bodega = new Bodega();
        bodega.setId(1L);
        bodega.setSucursal("Bodega Central");

        // El stock tiene 50 unidades disponibles
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setProducto(producto);
        stock.setBodega(bodega);
        stock.setCantidadDisponible(50);

        // El detalle de la venta pide 10 unidades a $990 c/u
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(10);
        detalle.setPrecioUnitario(990.0);

        Venta venta = new Venta();
        venta.setBodega(bodega);
        venta.setDetalles(List.of(detalle));

        when(stockRepository.findByProductoIdAndBodegaId(1L, 1L))
            .thenReturn(List.of(stock));
        when(stockRepository.save(any(Stock.class)))
            .thenAnswer(inv -> inv.getArgument(0));
        when(ventaRepository.save(any(Venta.class)))
            .thenAnswer(inv -> inv.getArgument(0));

        Venta resultado = ventaService.registrarVenta(venta);

        assertEquals(40, stock.getCantidadDisponible(),
            "El stock debería haber bajado de 50 a 40");
        assertEquals(9900.0, resultado.getTotal(),
            "El total debería ser 10 × $990 = $9.900");

        // Verificamos que se guardó el movimiento de VENTA
        verify(movimientoStockRepository, times(1)).save(any(MovimientoStock.class));
    }

    // PRUEBA 2: Venta falla si no hay suficiente stock
    @Test
    void registrarVenta_cuandoStockInsuficiente_lanzaExcepcion() {

        // ARRANGE — el stock solo tiene 5 unidades
        Producto producto = new Producto();
        producto.setId(2L);
        producto.setNombre("Queso Gauda 250g");

        Bodega bodega = new Bodega();
        bodega.setId(1L);

        Stock stock = new Stock();
        stock.setProducto(producto);
        stock.setBodega(bodega);
        stock.setCantidadDisponible(5); 

        // El detalle pide 20 unidades — más de lo disponible
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(20);        
        detalle.setPrecioUnitario(3490.0);

        Venta venta = new Venta();
        venta.setBodega(bodega);
        venta.setDetalles(List.of(detalle));

        when(stockRepository.findByProductoIdAndBodegaId(2L, 1L))
            .thenReturn(List.of(stock));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> ventaService.registrarVenta(venta),
            "Debería lanzar excepción por stock insuficiente"
        );

        assertTrue(ex.getMessage().contains("Stock insuficiente"),
            "El mensaje debe indicar stock insuficiente");

        // Verificamos que NO se guardó ninguna venta ni movimiento
        verify(ventaRepository, never()).save(any());
        verify(movimientoStockRepository, never()).save(any());
    }
}
