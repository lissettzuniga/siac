package dgtic.core.siac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiacController {

    @GetMapping("/reportes/inventario")
    public String reporteInventario() {
        return "paginas/reportes/inventario";
    }

    @GetMapping("/imagen/buscar")
    public String buscarImagen() {
        return "paginas/imagen/buscarPorImagen";
    }
}
