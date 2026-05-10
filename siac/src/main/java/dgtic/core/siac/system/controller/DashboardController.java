package dgtic.core.siac.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class DashboardController {

    @GetMapping("/api/dashboard/data")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("mensaje", "Dashboard SIAC");
        response.put("totalUsuarios", 0);
        response.put("totalProductos", 0);
        response.put("totalMovimientos", 0);
        response.put("totalReportes", 0);

        return ResponseEntity.ok(response);
    }
}
