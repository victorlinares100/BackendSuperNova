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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Stock_id")
    private Long id;

    // Relación con Producto
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    // Relación con Bodega
    @ManyToOne
    @JoinColumn(name = "id_bodega")
    private Bodega bodega;

    @Column(name = "Cantidad_Disponible")
    private Integer cantidadDisponible;

    @Column(name = "stock_minimo")       
    private Integer stockMinimo = 0; 

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}