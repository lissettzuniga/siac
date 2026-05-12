package dgtic.core.siac.system.service.busquedaImagen;

import dgtic.core.siac.system.dto.busquedaImagen.BusquedaImagenResponseDTO;
import dgtic.core.siac.system.mapper.ProductoMapper;
import dgtic.core.siac.system.model.Producto;
import dgtic.core.siac.system.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusquedaImagenServiceImpl implements BusquedaImagenService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public BusquedaImagenResponseDTO buscarPorImagen(MultipartFile imagen) {

        File archivoTemporal = null;

        try {

            archivoTemporal = File.createTempFile(
                    "ocr-",
                    imagen.getOriginalFilename()
            );

            imagen.transferTo(archivoTemporal);

            ITesseract tesseract = new Tesseract();

            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("eng");

            tesseract.setPageSegMode(6);
            tesseract.setOcrEngineMode(1);

            String textoDetectado = tesseract.doOCR(archivoTemporal);

            String textoLimpio = limpiarTexto(textoDetectado);

            List<Producto> productos = buscarCoincidencias(textoLimpio);

            return BusquedaImagenResponseDTO.builder()
                    .textoDetectado(textoLimpio)
                    .encontrado(!productos.isEmpty())
                    .productos(
                            productos.stream()
                                    .map(productoMapper::toResponseDTO)
                                    .toList()
                    )
                    .build();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al analizar la imagen con OCR: " + e.getMessage()
            );

        } finally {

            if (archivoTemporal != null && archivoTemporal.exists()) {
                archivoTemporal.delete();
            }
        }
    }

    private String limpiarTexto(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replaceAll("[^a-zA-Z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private List<Producto> buscarCoincidencias(String textoDetectado) {

        List<String> palabrasOCR = Arrays.stream(textoDetectado.split("\\s+"))
                .map(String::trim)
                .filter(p -> p.length() >= 3)
                .distinct()
                .toList();

        Pageable pageable = PageRequest.of(0, 50);

        return productoRepository.findByActivoTrue(pageable)
                .stream()
                .filter(producto -> {

                    String nombreProducto =
                            producto.getNombre().toLowerCase();

                    return palabrasOCR.stream().anyMatch(palabraOCR -> {

                        String palabra = palabraOCR.toLowerCase();

                        List<String> palabrasProducto =
                                Arrays.stream(nombreProducto.split("\\s+"))
                                        .toList();

                        return palabrasProducto.stream().anyMatch(palabraProducto -> {

                            int distancia =
                                    StringUtils.getLevenshteinDistance(
                                            palabra,
                                            palabraProducto
                                    );

                            return palabraProducto.contains(palabra)
                                    || distancia <= 2;
                        });
                    });
                })
                .distinct()
                .toList();
    }
}