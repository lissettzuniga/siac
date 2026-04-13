package dgtic.core.siac.service.categoria;

import dgtic.core.siac.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.dto.categoria.CategoriaResponseDTO;
import dgtic.core.siac.exception.ResourceNotFoundException;
import dgtic.core.siac.mapper.CategoriaMapper;
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
    public List<CategoriaResponseDTO> findAllActivos() {
        return categoriaRepository.findByActivoTrue()
                .stream()
                .map(CategoriaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CategoriaResponseDTO> findAllInactivos() {
        return categoriaRepository.findByActivoFalse()
                .stream()
                .map(CategoriaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        return CategoriaMapper.toResponseDTO(categoria);
    }


    @Override
    public CategoriaResponseDTO create(CategoriaRequestDTO request) {
        Categoria categoria = CategoriaMapper.toEntity(request);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return CategoriaMapper.toResponseDTO(categoriaGuardada);
    }

    @Override
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        CategoriaMapper.updateEntityFromDTO(request, categoria);

        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return CategoriaMapper.toResponseDTO(categoriaActualizada);
    }

    @Override
    public void activar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        categoria.setActivo(true);
        categoriaRepository.save(categoria);
    }

    @Override
    public void desactivar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }
}
