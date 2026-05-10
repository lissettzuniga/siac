package dgtic.core.siac.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImagenProductoNotFoundException extends ResourceNotFoundException {

    public ImagenProductoNotFoundException(Long id) {
        super("No se encontró la imagen con id: " + id);
    }
}