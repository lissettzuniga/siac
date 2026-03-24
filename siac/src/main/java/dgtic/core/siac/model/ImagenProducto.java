package dgtic.core.siac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagen_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen_producto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @NotBlank(message = "La ruta es obligatoria")
    @Column(nullable = false, length = 255)
    private String ruta;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Column(name = "nombre_archivo", nullable = false, length = 255)
    private String nombreArchivo;

    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

}
