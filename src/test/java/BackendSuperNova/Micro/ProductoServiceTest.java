package BackendSuperNova.Micro;

import BackendSuperNova.Micro.model.Producto;
import BackendSuperNova.Micro.repository.ProductoRepository;
import BackendSuperNova.Micro.service.ProductoService;
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
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void findAll_retornaListaDeProductos() {
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Producto 1");

        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Producto 2");

        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> resultado = productoService.findAll();

        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void findById_cuandoExiste_retornaProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Existente");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Producto Existente", resultado.getNombre());
    }

    @Test
    void findById_cuandoNoExiste_retornaNull() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = productoService.findById(99L);

        assertNull(resultado);
    }

    @Test
    void save_guardaYRetornaProducto() {
        Producto productoNuevo = new Producto();
        productoNuevo.setNombre("Nuevo Producto");

        Producto productoGuardado = new Producto();
        productoGuardado.setId(10L);
        productoGuardado.setNombre("Nuevo Producto");

        when(productoRepository.save(productoNuevo)).thenReturn(productoGuardado);

        Producto resultado = productoService.save(productoNuevo);

        assertNotNull(resultado.getId());
        assertEquals("Nuevo Producto", resultado.getNombre());
        verify(productoRepository, times(1)).save(productoNuevo);
    }

    @Test
    void delete_llamaAlRepositorioCorrectamente() {
        doNothing().when(productoRepository).deleteById(1L);
        
        productoService.delete(1L);
        
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_llamaAlRepositorioCorrectamente() {
        doNothing().when(productoRepository).deleteById(2L);
        
        productoService.deleteById(2L);
        
        verify(productoRepository, times(1)).deleteById(2L);
    }
}