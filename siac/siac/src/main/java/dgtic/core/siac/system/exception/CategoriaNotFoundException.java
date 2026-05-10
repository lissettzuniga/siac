package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoriaNotFoundException extends ResourceNotFoundException {

    public CategoriaNotFoundException(Long id) {
        super("Categoría no encontrada con id: " + id);
    }

    public CategoriaNotFoundException(String nombre) {
        super("Categoría no encontrada con nombre: " + nombre);
    }
}