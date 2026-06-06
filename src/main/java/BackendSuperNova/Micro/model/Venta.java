package BackendSuperNova.Micro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Venta_id")
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleVenta> detalles;

    @PrePersist
    public void prePersist() {
        if (fechaVenta == null) fechaVenta = LocalDateTime.now();
    }
}