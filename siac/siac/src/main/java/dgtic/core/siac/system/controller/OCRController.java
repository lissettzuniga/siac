package dgtic.core.siac.system.controller;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OCRController {

    @PostMapping
    public ResponseEntity<String> leerTexto(
            @RequestParam("imagen") MultipartFile imagen) {

        try {

            File archivoTemporal =
                    File.createTempFile("ocr-", imagen.getOriginalFilename());

            imagen.transferTo(archivoTemporal);

            ITesseract tesseract = new Tesseract();

            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("eng");

            String texto = tesseract.doOCR(archivoTemporal);

            archivoTemporal.delete();

            return ResponseEntity.ok(texto);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("ERROR OCR: " + e.getMessage());
        }
    }
}