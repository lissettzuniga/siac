package dgtic.core.siac.service.productoCarta;

import dgtic.core.siac.model.Categoria;

import dgtic.core.siac.model.ProductoCarta;
import dgtic.core.siac.repository.ProductoCartaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoCartaServiceImpl implements ProductoCartaService {

    private final ProductoCartaRepository productoCartaRepository;

    public ProductoCartaServiceImpl(ProductoCartaRepository productoCartaRepository) {
        this.productoCartaRepository = productoCartaRepository;
    }

    @Override
    public List<ProductoCarta> findAllActivos() {
        return productoCartaRepository.findByActivoTrue();
    }

    @Override
    public List<ProductoCarta> findAllInactivos() {
        return productoCartaRepository.findByActivoFalse();
    }

    @Override
    public Optional<ProductoCarta> findById(Long id) {
        return productoCartaRepository.findById(id);
    }

    @Override
    public ProductoCarta save(ProductoCarta productoCarta) {
        return productoCartaRepository.save(productoCarta);
    }

    @Override
    public void activar(Long id) {
        ProductoCarta productoCarta = productoCartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto carta no encontrado"));
        productoCarta.setActivo(true);
        productoCartaRepository.save(productoCarta);
    }

    @Override
    public void desactivar(Long id) {
        ProductoCarta productoCarta = productoCartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto carta no encontrado"));
        productoCarta.setActivo(false);
        productoCartaRepository.save(productoCarta);
    }
}
