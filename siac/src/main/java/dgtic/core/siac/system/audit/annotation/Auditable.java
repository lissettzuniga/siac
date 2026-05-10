package dgtic.core.siac.system.audit.annotation;

import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {

    EntidadEnum entidad();

    AccionEnum accion();

    String descripcion() default "";
}
