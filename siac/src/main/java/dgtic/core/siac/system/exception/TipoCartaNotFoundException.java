package dgtic.core.siac.system.exception;

public class TipoCartaNotFoundException extends ResourceNotFoundException {

    public TipoCartaNotFoundException(Long id) {
        super("No se encontró el tipo de carta con id: " + id);
    }

    public TipoCartaNotFoundException(String nombre) {
        super("No se encontró el tipo de carta con nombre: " + nombre);
    }
}
