package dgtic.core.siac.service.tipoCarta;

import dgtic.core.siac.model.TipoCarta;
import dgtic.core.siac.repository.TipoCartaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCartaServiceImpl implements TipoCartaService {

    private final TipoCartaRepository tipoCartaRepository;

    public TipoCartaServiceImpl(TipoCartaRepository tipoCartaRepository) {
        this.tipoCartaRepository = tipoCartaRepository;
    }

    @Override
    public List<TipoCarta> findAllActivos() {
        return tipoCartaRepository.findByActivoTrue();
    }

    @Override
    public List<TipoCarta> findAllInactivos() {
        return tipoCartaRepository.findByActivoFalse();
    }

    @Override
    public Optional<TipoCarta> findById(Long id) {
        return tipoCartaRepository.findById(id);
    }

    @Override
    public TipoCarta save(TipoCarta tipoCarta) {
        return tipoCartaRepository.save(tipoCarta);
    }

    @Override
    public void activar(Long id) {
        TipoCarta tipoCarta = tipoCartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo carta no encontrado"));

        tipoCarta.setActivo(true);
        tipoCartaRepository.save(tipoCarta);
    }

    @Override
    public void desactivar(Long id) {
        TipoCarta tipoCarta = tipoCartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo carta no encontrado"));

        tipoCarta.setActivo(false);
        tipoCartaRepository.save(tipoCarta);
    }
}
