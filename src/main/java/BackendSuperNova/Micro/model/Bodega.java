package BackendSuperNova.Micro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bodega")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bodega_id")
    private Long id;

    @Column(name = "Sucursal", length = 100)
    private String sucursal;

    @Column(name = "Direccion", length = 200)
    private String direccion;
}