package dgtic.core.siac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(
        name = "permiso",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"accion", "recurso"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La acción es obligatoria")
    @Column(nullable = false, length = 100)
    private String accion;

    @NotBlank(message = "El recurso es obligatorio")
    @Column(nullable = false, length = 100)
    private String recurso;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
