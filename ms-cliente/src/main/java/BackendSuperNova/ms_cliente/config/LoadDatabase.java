package BackendSuperNova.ms_cliente.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import BackendSuperNova.ms_cliente.model.Bodega;
import BackendSuperNova.ms_cliente.repository.BodegaRepository;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BodegaRepository bodegaRepo) {

        return args -> {
            // ─── Solo cargamos Bodegas en este microservicio ───
            if (bodegaRepo.count() == 0) {
                System.out.println("Cargando bodegas en ms-cliente...");
                bodegaRepo.save(new Bodega(null, "Bodega Central", "Av. Principal 123, Santiago"));
                bodegaRepo.save(new Bodega(null, "Bodega Norte",   "Av. Los Leones 456, Providencia"));
                bodegaRepo.save(new Bodega(null, "Bodega Sur",     "Calle Gran Avenida 789, San Miguel"));
            }

            System.out.println("✓ Base de datos de Clientes lista con bodegas de prueba.");
        };
    }
}