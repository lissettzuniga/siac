package dgtic.core.siac.service.rol;

import dgtic.core.siac.model.Producto;
import dgtic.core.siac.model.Rol;
import dgtic.core.siac.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService{

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> findAllActivos() {
        return rolRepository.findByActivoTrue();
    }

    @Override
    public List<Rol> findAllInactivos() {
        return rolRepository.findByActivoFalse();
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void activar(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rol.setActivo(true);
        rolRepository.save(rol);
    }

    @Override
    public void desactivar(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rol.setActivo(false);
        rolRepository.save(rol);
    }
}
