package dgtic.core.siac.system.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends ResourceNotFoundException {

    public UsuarioNotFoundException(Long id) {
        super("Usuario no encontrado con id: " + id);
    }

    public UsuarioNotFoundException(String correo) {
        super("Usuario no encontrado con correo: " + correo);
    }
}