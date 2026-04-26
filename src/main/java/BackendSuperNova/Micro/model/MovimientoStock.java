package BackendSuperNova.Micro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Movimiento_Stock")
public class MovimientoStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Movimiento_Stock_id")
    private Long id;

    // Relación con Stock
    @ManyToOne
    @JoinColumn(name = "Stock_id")
    private Stock stock;

    @Column(name = "cantidad")
    private Integer cantidad;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "fecha_movimiento")
    private LocalDate fechaMovimiento;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}