package dgtic.core.siac.system.service.bitacoraMovimiento;

import dgtic.core.siac.security.SecurityUtils;
import dgtic.core.siac.system.dto.bitacoraMovimiento.BitacoraMovimientoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.ResourceNotFoundException;
import dgtic.core.siac.system.mapper.BitacoraMovimientoMapper;
import dgtic.core.siac.system.model.BitacoraMovimiento;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.repository.BitacoraMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BitacoraMovimientoServiceImpl implements BitacoraMovimientoService {

    private final BitacoraMovimientoRepository bitacoraMovimientoRepository;
    private final BitacoraMovimientoMapper bitacoraMovimientoMapper;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public Page<BitacoraMovimientoResponseDTO> findAll(Pageable pageable) {
        return bitacoraMovimientoRepository.findAll(pageable)
                .map(bitacoraMovimientoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BitacoraMovimientoResponseDTO> findByUsuarioId(Long usuarioId, Pageable pageable) {
        return bitacoraMovimientoRepository.findByUsuarioId(usuarioId, pageable)
                .map(bitacoraMovimientoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BitacoraMovimientoResponseDTO> findByEntidad(EntidadEnum entidad, Pageable pageable) {
        return bitacoraMovimientoRepository.findByEntidad(entidad, pageable)
                .map(bitacoraMovimientoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public BitacoraMovimientoResponseDTO findById(Long id) {
        BitacoraMovimiento bitacora = bitacoraMovimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el registro de bitácora con id: " + id
                ));

        return bitacoraMovimientoMapper.toResponseDTO(bitacora);
    }

    @Override
    @Transactional
    public void logAction(
            EntidadEnum entidad,
            AccionEnum accion,
            String descripcion
    ) {
        Usuario usuarioAutenticado = securityUtils.getUsuarioAutenticado();

        BitacoraMovimiento bitacora = BitacoraMovimiento.builder()
                .usuario(usuarioAutenticado)
                .entidad(entidad)
                .accion(accion)
                .descripcion(descripcion)
                .build();

        bitacoraMovimientoRepository.save(bitacora);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BitacoraMovimientoResponseDTO> findByUsuarioIdAndEntidad(
            Long usuarioId,
            EntidadEnum entidad,
            Pageable pageable
    ) {
        return bitacoraMovimientoRepository.findByUsuarioIdAndEntidad(usuarioId, entidad, pageable)
                .map(bitacoraMovimientoMapper::toResponseDTO);
    }
}