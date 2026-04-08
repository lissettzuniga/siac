package dgtic.core.siac.service.permiso;

import dgtic.core.siac.model.Permiso;
import dgtic.core.siac.repository.PermisoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoInterfaceImpl implements PermisoInterface{

    private final PermisoRepository permisoRepository;

    public PermisoInterfaceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Override
    public List<Permiso> findAllActivos() {
        return permisoRepository.findByActivoTrue();
    }

    @Override
    public List<Permiso> findAllInactivos() {
        return permisoRepository.findByActivoFalse();
    }

    @Override
    public Optional<Permiso> findById(Long id) {
        return permisoRepository.findById(id);
    }

    @Override
    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public void activar(Long id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        permiso.setActivo(true);
        permisoRepository.save(permiso);

    }

    @Override
    public void desactivar(Long id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        permiso.setActivo(false);
        permisoRepository.save(permiso);
    }
}
