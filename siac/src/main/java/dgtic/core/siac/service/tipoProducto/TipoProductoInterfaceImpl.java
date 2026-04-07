package dgtic.core.siac.service.tipoProducto;

import dgtic.core.siac.model.TipoProducto;
import dgtic.core.siac.repository.TipoProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProductoInterfaceImpl implements TipoProductoInterface {

    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoInterfaceImpl(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    @Override
    public List<TipoProducto> findAllActivos() {
        return tipoProductoRepository.findByActivoTrue();
    }

    @Override
    public List<TipoProducto> findAllInactivos() {
        return tipoProductoRepository.findByActivoFalse();
    }

    @Override
    public Optional<TipoProducto> findById(Long id) {
        return tipoProductoRepository.findById(id);
    }

    @Override
    public TipoProducto save(TipoProducto tipoProducto) {
        return tipoProductoRepository.save(tipoProducto);
    }

    @Override
    public void activar(Long id) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo producto no encontrado"));

        tipoProducto.setActivo(true);
        tipoProductoRepository.save(tipoProducto);
    }

    @Override
    public void desactivar(Long id) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo producto no encontrado"));

        tipoProducto.setActivo(false);
        tipoProductoRepository.save(tipoProducto);
    }
}
