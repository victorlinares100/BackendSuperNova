package BackendSuperNova.ms_inventario;

import BackendSuperNova.ms_inventario.model.*;
import BackendSuperNova.ms_inventario.repository.*;
import BackendSuperNova.ms_inventario.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock private StockRepository           stockRepository;
    @Mock private MovimientoStockRepository movimientoStockRepository;
    @Mock private ProductoRepository        productoRepository; 

    @InjectMocks private StockService stockService;

    @Test
    void guardarStock_creaMovimientoDeEntradaAutomaticamente() {
        // ARRANGE
        Producto producto = new Producto();
        producto.setId(3L);
        producto.setNombre("Agua Mineral 1.5L");

        Stock stock = new Stock();
        stock.setId(10L);
        stock.setProducto(producto);
        stock.setBodegaId(1L);      
        stock.setCantidadDisponible(100);

        when(productoRepository.findById(3L)).thenReturn(Optional.of(producto));
        
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockRepository.findById(10L)).thenReturn(Optional.of(stock));

        stockService.save(stock);

        verify(movimientoStockRepository, times(1)).save(argThat(mov ->
            "ENTRADA".equals(mov.getTipoMovimiento()) &&
            mov.getCantidad() == 100
        ));
    }
}