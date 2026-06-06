package BackendSuperNova.Micro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Detalle_Venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Detalle_Venta_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Venta_id")
    @JsonBackReference
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "Producto_id")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "Bodega_id")
    private Bodega bodega;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;
}