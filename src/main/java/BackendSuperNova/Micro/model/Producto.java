package BackendSuperNova.Micro.model;

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
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long id;

    @Column(name = "codigo_de_barras", length = 50)
    private String codigoDeBarras;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "stock_minimo")
    private Integer stockMinimo = 0;

    @ManyToOne
    @JoinColumn(name = "Categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "Proveedor_id")
    private Proveedor proveedor;
}