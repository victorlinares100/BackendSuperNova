package BackendSuperNova.Micro;

import BackendSuperNova.Micro.model.Bodega;
import BackendSuperNova.Micro.repository.BodegaRepository;
import BackendSuperNova.Micro.service.BodegaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodegaServiceTest {

    @Mock
    private BodegaRepository bodegaRepository;

    @InjectMocks
    private BodegaService bodegaService;

    @Test
    void findAll_retornaListaDeBodegas() {
        Bodega b1 = new Bodega(1L, "Bodega Central", "Direccion 1");
        Bodega b2 = new Bodega(2L, "Bodega Norte", "Direccion 2");
        when(bodegaRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Bodega> resultado = bodegaService.findAll();

        assertEquals(2, resultado.size());
        verify(bodegaRepository, times(1)).findAll();
    }

    @Test
    void findById_cuandoExiste_retornaBodega() {
        Bodega bodega = new Bodega(1L, "Bodega Central", "Direccion 1");
        when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));

        Bodega resultado = bodegaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Bodega Central", resultado.getSucursal());
    }

    @Test
    void findById_cuandoNoExiste_retornaNull() {
        when(bodegaRepository.findById(99L)).thenReturn(Optional.empty());

        Bodega resultado = bodegaService.findById(99L);

        assertNull(resultado);
    }

    @Test
    void save_guardaYRetornaBodega() {
        Bodega bodegaNueva = new Bodega(null, "Bodega Nueva", "Direccion 3");
        Bodega bodegaGuardada = new Bodega(3L, "Bodega Nueva", "Direccion 3");
        
        when(bodegaRepository.save(bodegaNueva)).thenReturn(bodegaGuardada);

        Bodega resultado = bodegaService.save(bodegaNueva);

        assertNotNull(resultado.getId());
        assertEquals("Bodega Nueva", resultado.getSucursal());
        verify(bodegaRepository, times(1)).save(bodegaNueva);
    }

    @Test
    void delete_llamaAlRepositorioCorrectamente() {
        doNothing().when(bodegaRepository).deleteById(1L);
        
        bodegaService.delete(1L);
        
        verify(bodegaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_llamaAlRepositorioCorrectamente() {
        doNothing().when(bodegaRepository).deleteById(1L);
        
        bodegaService.deleteById(1L);
        
        verify(bodegaRepository, times(1)).deleteById(1L);
    }
}