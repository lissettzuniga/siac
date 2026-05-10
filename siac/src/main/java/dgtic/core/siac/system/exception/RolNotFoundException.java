package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RolNotFoundException extends ResourceNotFoundException {

    public RolNotFoundException(Long id) {
        super("Rol no encontrado con id: " + id);
    }

    public RolNotFoundException(String nombre) {
        super("Rol no encontrado con nombre: " + nombre);
    }
}