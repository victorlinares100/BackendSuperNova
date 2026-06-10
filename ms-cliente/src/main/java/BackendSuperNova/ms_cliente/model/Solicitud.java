package BackendSuperNova.ms_cliente.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Solicitud_id")
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "mensaje", columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @Column(name = "tipo")
    private String tipo = "CONSULTA";

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @Column(name = "estado")
    private String estado = "PENDIENTE";

    @PrePersist
    public void prePersist() {
        if (fechaSolicitud == null) fechaSolicitud = LocalDateTime.now();
    }
}