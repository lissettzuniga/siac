package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductoCartaNotFoundException extends ResourceNotFoundException {

    public ProductoCartaNotFoundException(Long productoId) {
        super("No se encontró el detalle de carta para el producto con id: " + productoId);
    }
}
