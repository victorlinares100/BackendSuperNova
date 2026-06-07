package BackendSuperNova.Micro;

import BackendSuperNova.Micro.model.Categoria;
import BackendSuperNova.Micro.repository.CategoriaRepository;
import BackendSuperNova.Micro.service.CategoriaService;
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
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void findAll_retornaListaDeCategorias() {
        Categoria c1 = new Categoria();
        c1.setId(1L);
        c1.setNombre_Categoria("Lácteos");

        Categoria c2 = new Categoria();
        c2.setId(2L);
        c2.setNombre_Categoria("Bebidas");

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Categoria> resultado = categoriaService.findAll();

        assertEquals(2, resultado.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void findById_cuandoExiste_retornaCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre_Categoria("Lácteos");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Lácteos", resultado.getNombre_Categoria());
    }

    @Test
    void findById_cuandoNoExiste_retornaNull() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        Categoria resultado = categoriaService.findById(99L);

        assertNull(resultado);
    }

    @Test
    void save_guardaYRetornaCategoria() {
        Categoria categoriaNueva = new Categoria();
        categoriaNueva.setNombre_Categoria("Nueva Categoría");

        Categoria categoriaGuardada = new Categoria();
        categoriaGuardada.setId(10L);
        categoriaGuardada.setNombre_Categoria("Nueva Categoría");

        when(categoriaRepository.save(categoriaNueva)).thenReturn(categoriaGuardada);

        Categoria resultado = categoriaService.save(categoriaNueva);

        assertNotNull(resultado.getId());
        assertEquals("Nueva Categoría", resultado.getNombre_Categoria());
        verify(categoriaRepository, times(1)).save(categoriaNueva);
    }

    @Test
    void delete_llamaAlRepositorioCorrectamente() {
        doNothing().when(categoriaRepository).deleteById(1L);
        
        categoriaService.delete(1L);
        
        verify(categoriaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_llamaAlRepositorioCorrectamente() {
        doNothing().when(categoriaRepository).deleteById(2L);
        
        categoriaService.deleteById(2L);
        
        verify(categoriaRepository, times(1)).deleteById(2L);
    }
}