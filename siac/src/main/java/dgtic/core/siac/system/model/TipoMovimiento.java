package dgtic.core.siac.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tipo_movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //visible para el usuario
    @NotBlank(message = "El nombre del tipo de movimiento es obligatorio")
    @Column(nullable = false,unique = true, length = 100)
    private String nombre;

    //lógica del sistema
    @NotBlank(message = "La clave del tipo de movimiento es obligatoria")
    @Column(nullable = false, unique = true, length = 50)
    private String clave;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
