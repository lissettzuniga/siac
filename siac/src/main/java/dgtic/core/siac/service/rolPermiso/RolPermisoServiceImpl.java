package dgtic.core.siac.service.rolPermiso;

import dgtic.core.siac.model.RolPermiso;
import dgtic.core.siac.repository.RolPermisoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;

    public RolPermisoServiceImpl(RolPermisoRepository rolPermisoRepository) {
        this.rolPermisoRepository = rolPermisoRepository;
    }

    @Override
    public List<RolPermiso> findAllActivos() {
        return rolPermisoRepository.findByActivoTrue();
    }

    @Override
    public List<RolPermiso> findAllInactivos() {
        return rolPermisoRepository.findByActivoFalse();
    }

    @Override
    public Optional<RolPermiso> findById(Long id) {
        return rolPermisoRepository.findById(id);
    }

    @Override
    public RolPermiso save(RolPermiso rolPermiso) {
        return rolPermisoRepository.save(rolPermiso);
    }

    @Override
    public void activar(Long id) {
        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rolpermiso no encontrado"));

        rolPermiso.setActivo(true);
        rolPermisoRepository.save(rolPermiso);
    }

    @Override
    public void desactivar(Long id) {
        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rolpermiso no encontrado"));

        rolPermiso.setActivo(false);
        rolPermisoRepository.save(rolPermiso);
    }
}
