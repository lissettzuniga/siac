package dgtic.core.siac.service.categoria;

import dgtic.core.siac.model.Categoria;
import dgtic.core.siac.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Categoria> findAllActivos() {
        return categoriaRepository.findByActivoTrue();
    }

    @Override
    public List<Categoria> findAllInactivos() {
        return categoriaRepository.findByActivoFalse();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void activar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoria.setActivo(true);
        categoriaRepository.save(categoria);
    }

    @Override
    public void desactivar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }
}
