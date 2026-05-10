package dgtic.core.siac.system.exception;

public class RolPermisoNotFoundException extends ResourceNotFoundException {

    public RolPermisoNotFoundException(Long id) {
        super("No se encontró la relación rol-permiso con id: " + id);
    }
}
