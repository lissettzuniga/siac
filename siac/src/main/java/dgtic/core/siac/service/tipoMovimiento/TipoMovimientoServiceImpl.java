package dgtic.core.siac.service.tipoMovimiento;

import dgtic.core.siac.model.TipoMovimiento;
import dgtic.core.siac.repository.TipoMovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

    private final TipoMovimientoRepository tipoMovimientoRepository;

    public TipoMovimientoServiceImpl(TipoMovimientoRepository tipoMovimientoRepository) {
        this.tipoMovimientoRepository = tipoMovimientoRepository;
    }


    @Override
    public List<TipoMovimiento> findAllActivos() {
        return tipoMovimientoRepository.findByActivoTrue();
    }

    @Override
    public List<TipoMovimiento> findAllInactivos() {
        return tipoMovimientoRepository.findByActivoFalse();
    }

    @Override
    public Optional<TipoMovimiento> findById(Long id) {
        return tipoMovimientoRepository.findById(id);
    }

    @Override
    public TipoMovimiento save(TipoMovimiento tipoMovimiento) {
        return tipoMovimientoRepository.save(tipoMovimiento);
    }

    @Override
    public void activar(Long id) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo movimiento no encontrado"));

        tipoMovimiento.setActivo(true);
        tipoMovimientoRepository.save(tipoMovimiento);
    }

    @Override
    public void desactivar(Long id) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo movimiento no encontrado"));

        tipoMovimiento.setActivo(false);
        tipoMovimientoRepository.save(tipoMovimiento);
    }
}
