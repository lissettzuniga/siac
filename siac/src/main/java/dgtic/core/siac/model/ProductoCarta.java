package dgtic.core.siac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_carta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCarta {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @NotNull(message = "El tipo de carta es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_carta", nullable = false)
    private TipoCarta tipoCarta;

    @NotBlank(message = "El atributo es obligatorio")
    @Column(length = 100, nullable = false)
    private String atributo;

    @NotNull(message = "El ataque es obligatorio")
    @PositiveOrZero(message = "El ataque no puede ser negativo")
    @Column(nullable = false)
    private Integer ataque;

    @NotNull(message = "La defensa es obligatoria")
    @PositiveOrZero(message = "La defensa no puede ser negativa")
    @Column(nullable = false)
    private Integer defensa;

    @NotNull(message = "El nivel es obligatorio")
    @Positive(message = "El nivel debe ser mayor que cero")
    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean activo;
}
