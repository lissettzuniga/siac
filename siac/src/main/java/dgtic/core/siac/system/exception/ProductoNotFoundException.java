package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends ResourceNotFoundException {

    public ProductoNotFoundException(Long id) {
        super("Producto no encontrado con id: " + id);
    }

    public ProductoNotFoundException(String nombre) {
        super("Producto no encontrado con nombre: " + nombre);
    }


}