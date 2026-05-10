package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TipoMovimientoNotFoundException extends ResourceNotFoundException {

    public TipoMovimientoNotFoundException(Long id) {
        super("Tipo de movimiento no encontrado con id: " + id);
    }

    public TipoMovimientoNotFoundException(String clave) {
        super("Tipo de movimiento no encontrado con clave: " + clave);
    }
}
