package dgtic.core.siac.model;

import dgtic.core.siac.enums.AccionEnum;
import dgtic.core.siac.enums.EntidadEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora_movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BitacoraMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // ENUM entidad
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EntidadEnum entidad;

    @NotNull(message = "El id de la entidad es obligatorio")
    @Column(name = "id_entidad", nullable = false)
    private Long idEntidad;

    // ENUM acción
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccionEnum accion;


    @Column(length = 255)
    private String descripcion;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

}
