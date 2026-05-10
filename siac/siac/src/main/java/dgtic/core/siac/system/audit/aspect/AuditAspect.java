package dgtic.core.siac.system.audit.aspect;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.service.bitacoraMovimiento.BitacoraMovimientoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final BitacoraMovimientoService bitacoraMovimientoService;

    @AfterReturning("@annotation(auditable)")
    public void audit(JoinPoint joinPoint, Auditable auditable) {

        EntidadEnum entidad = auditable.entidad();
        AccionEnum accion = auditable.accion();
        String descripcion = auditable.descripcion();

        log.info("Auditando acción {} sobre entidad {}", accion, entidad);

        bitacoraMovimientoService.logAction(
                entidad,
                accion,
                descripcion
        );
    }
}