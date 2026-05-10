package dgtic.core.siac.system.service.categoria;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.system.dto.categoria.CategoriaResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.CategoriaNotFoundException;
import dgtic.core.siac.system.exception.DuplicateResourceException;

import dgtic.core.siac.system.mapper.CategoriaMapper;
import dgtic.core.siac.system.model.Categoria;
import dgtic.core.siac.system.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;


    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaResponseDTO> findAllActivos(Pageable pageable) {
        return categoriaRepository.findByActivoTrue(pageable)
                .map(categoriaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaResponseDTO> findAllInactivos(Pageable pageable) {
        return categoriaRepository.findByActivoFalse(pageable)
                .map(categoriaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        return categoriaMapper.toResponseDTO(categoria);
    }


    @Auditable(
            entidad = EntidadEnum.CATEGORIA,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó una categoría"
    )
    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO request) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe una categoría con el nombre: " + request.getNombre()
            );
        }
        Categoria categoria = categoriaMapper.toEntity(request);
        categoria.setActivo(true);

        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(categoriaGuardada);
    }


    @Auditable(
            entidad = EntidadEnum.CATEGORIA,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó una categoría"
    )
    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        if (categoriaRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otra categoría con el nombre: " + request.getNombre()
            );
        }

        categoriaMapper.updateEntityFromDTO(request, categoria);

        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(categoriaActualizada);
    }


    @Auditable(
            entidad = EntidadEnum.CATEGORIA,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó una categoría"
    )
    @Override
    @Transactional
    public void deactivate(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        if (!categoria.getActivo()) {
            return;
        }

        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }



    @Auditable(
            entidad = EntidadEnum.CATEGORIA,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó una categoría"
    )
    @Override
    @Transactional
    public void activate(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        if (categoria.getActivo()) {
            return;
        }

        categoria.setActivo(true);
        categoriaRepository.save(categoria);
    }


}