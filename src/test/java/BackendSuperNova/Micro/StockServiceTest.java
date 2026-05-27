package BackendSuperNova.Micro;

import BackendSuperNova.Micro.model.*;
import BackendSuperNova.Micro.repository.StockRepository;
import BackendSuperNova.Micro.repository.MovimientoStockRepository;
import BackendSuperNova.Micro.service.StockService;
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

    @InjectMocks private StockService stockService;

    // PRUEBA 3: Guardar stock crea movimiento de ENTRADA
    @Test
    void guardarStock_creaMovimientoDeEntradaAutomaticamente() {

        Producto producto = new Producto();
        producto.setId(3L);
        producto.setNombre("Agua Mineral 1.5L");

        Bodega bodega = new Bodega();
        bodega.setId(1L);
        bodega.setSucursal("Bodega Central");

        Stock stock = new Stock();
        stock.setId(10L);
        stock.setProducto(producto);
        stock.setBodega(bodega);
        stock.setCantidadDisponible(100);

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockRepository.findById(10L)).thenReturn(Optional.of(stock));

        stockService.save(stock);

        verify(movimientoStockRepository, times(1)).save(argThat(mov ->
            "ENTRADA".equals(mov.getTipoMovimiento()) &&
            mov.getCantidad() == 100
        ));
    }
}
