package dgtic.core.siac.system.exception;

public class UsuarioRolNotFoundException extends ResourceNotFoundException {

    public UsuarioRolNotFoundException(Long id) {
        super("No se encontró la relación usuario-rol con id: " + id);
    }
}