package dgtic.core.siac.service.bitacoraMovimiento;

import dgtic.core.siac.model.BitacoraMovimiento;
import dgtic.core.siac.repository.BitacoraMovimientoRepository;
import dgtic.core.siac.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BitacoraMovimientoInterfaceImpl implements BitacoraMovimientoInterface{

    private final BitacoraMovimientoRepository bitacoraMovimientoRepository;
    private final UsuarioRepository usuarioRepository;

    public BitacoraMovimientoInterfaceImpl(BitacoraMovimientoRepository bitacoraMovimientoRepository, UsuarioRepository usuarioRepository) {
        this.bitacoraMovimientoRepository = bitacoraMovimientoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public List<BitacoraMovimiento> findAll() {
        return bitacoraMovimientoRepository.findAll();
    }

    @Override
    public Optional<BitacoraMovimiento> findById(Long id) {
        return bitacoraMovimientoRepository.findById(id);
    }

    @Override
    public List<BitacoraMovimiento> findByUsuario(Long usuarioId) {
        return bitacoraMovimientoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<BitacoraMovimiento> findByEntidad(String entidad) {
        return bitacoraMovimientoRepository.findByEntidad(entidad);
    }

    @Override
    public BitacoraMovimiento save(BitacoraMovimiento movimiento) {
        return bitacoraMovimientoRepository.save(movimiento);
    }
}
