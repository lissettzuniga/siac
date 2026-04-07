package dgtic.core.siac.controller;

import dgtic.core.siac.dto.ProductoFormDTO;
import dgtic.core.siac.repository.CategoriaRepository;
import dgtic.core.siac.repository.TipoProductoRepository;
import dgtic.core.siac.service.producto.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    TipoProductoRepository tipoProductoRepository;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("productos", productoService.findAllActivos());
        model.addAttribute("productosInactivos", productoService.findAllInactivos());
        return "paginas/productos/listaProductos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("productoForm", new ProductoFormDTO());
        model.addAttribute("modoEdicion", false);
        cargarCatalogos(model);
        return "paginas/productos/formularioProducto";
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("productoForm") ProductoFormDTO productoForm,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicion", false);
            cargarCatalogos(model);
            return "paginas/productos/formularioProducto";
        }

        productoService.save(productoForm);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("productoForm", productoService.findFormularioById(id));
        model.addAttribute("modoEdicion", true);
        cargarCatalogos(model);
        return "paginas/productos/formularioProducto";
    }

    @PostMapping("/actualizar/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("productoForm") ProductoFormDTO productoForm,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicion", true);
            cargarCatalogos(model);
            return "paginas/productos/formularioProducto";
        }

        productoService.update(id, productoForm);
        return "redirect:/productos";
    }

    @PostMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        productoService.activar(id);
        return "redirect:/productos";
    }

    @PostMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        productoService.desactivar(id);
        return "redirect:/productos";
    }

    private void cargarCatalogos(Model model) {
        model.addAttribute("categorias", categoriaRepository.findByActivoTrue());
        model.addAttribute("tiposProducto", tipoProductoRepository.findByActivoTrue());
    }


}
