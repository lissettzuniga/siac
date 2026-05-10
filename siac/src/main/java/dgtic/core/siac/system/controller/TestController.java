package dgtic.core.siac.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/public/info")
    public String publicInfo() {
        return "Endpoint público funcionando";
    }

    @GetMapping("/api/dashboard")
    public String dashboard() {
        return "Dashboard protegido de SIAC";
    }
}