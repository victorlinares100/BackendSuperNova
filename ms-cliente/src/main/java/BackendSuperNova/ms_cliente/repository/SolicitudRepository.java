package BackendSuperNova.ms_cliente.repository;

import BackendSuperNova.ms_cliente.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
}