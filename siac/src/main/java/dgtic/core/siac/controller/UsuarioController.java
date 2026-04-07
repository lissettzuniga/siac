package dgtic.core.siac.controller;

import dgtic.core.siac.dto.UsuarioFormDTO;
import dgtic.core.siac.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("usuarios", usuarioService.findAllActivos());
        model.addAttribute("usuariosInactivos", usuarioService.findAllInactivos());
        return "paginas/usuarios/listaUsuarios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new UsuarioFormDTO());
        model.addAttribute("modoEdicion", false);
        return "paginas/usuarios/formUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") UsuarioFormDTO usuarioFormDTO,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicion", false);
            return "paginas/usuarios/formUsuario";
        }

        try {
            usuarioService.save(usuarioFormDTO);
        } catch (RuntimeException e) {
            model.addAttribute("modoEdicion", false);
            model.addAttribute("errorCorreo", e.getMessage());
            return "paginas/usuarios/formUsuario";
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        UsuarioFormDTO usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("modoEdicion", true);
        return "paginas/usuarios/formUsuario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id,
                                    @Valid @ModelAttribute("usuario") UsuarioFormDTO usuarioFormDTO,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicion", true);
            return "paginas/usuarios/formUsuario";
        }

        try {
            usuarioService.update(id, usuarioFormDTO);
        } catch (RuntimeException e) {
            model.addAttribute("modoEdicion", true);
            model.addAttribute("errorCorreo", e.getMessage());
            return "paginas/usuarios/formUsuario";
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/activar/{id}")
    public String activarUsuario(@PathVariable Long id) {
        usuarioService.activar(id);
        return "redirect:/usuarios";
    }
}