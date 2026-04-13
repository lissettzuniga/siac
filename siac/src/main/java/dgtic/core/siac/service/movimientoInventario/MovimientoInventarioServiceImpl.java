package dgtic.core.siac.service.movimientoInventario;

import dgtic.core.siac.model.MovimientoInventario;
import dgtic.core.siac.repository.MovimientoInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public MovimientoInventarioServiceImpl(MovimientoInventarioRepository movimientoInventarioRepository) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    @Override
    public List<MovimientoInventario> findAllActivos() {
        return movimientoInventarioRepository.findByActivoTrue();
    }

    @Override
    public List<MovimientoInventario> findAllInactivos() {
        return movimientoInventarioRepository.findByActivoFalse();
    }

    @Override
    public Optional<MovimientoInventario> findById(Long id) {
        return movimientoInventarioRepository.findById(id);
    }

    @Override
    public MovimientoInventario save(MovimientoInventario movimientoInventario) {
        return movimientoInventarioRepository.save(movimientoInventario);
    }

    @Override
    public void activar(Long id) {
        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento inventario no encontrado"));

        movimientoInventario.setActivo(true);
        movimientoInventarioRepository.save(movimientoInventario);
    }

    @Override
    public void desactivar(Long id) {
        MovimientoInventario movimientoInventario = movimientoInventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento inventario no encontrado"));

        movimientoInventario.setActivo(false);
        movimientoInventarioRepository.save(movimientoInventario);
    }
}
