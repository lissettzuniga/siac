package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PermisoNotFoundException extends ResourceNotFoundException {

    public PermisoNotFoundException(Long id) {
        super("No se encontró el permiso con id: " + id);
    }
}