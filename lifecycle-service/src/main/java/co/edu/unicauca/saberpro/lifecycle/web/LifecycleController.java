package co.edu.unicauca.saberpro.lifecycle.web;

import co.edu.unicauca.saberpro.lifecycle.service.LifecycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** Endpoints de consulta (solo para verificar el resultado del procesamiento asincrono). */
@RestController
@RequestMapping("/lifecycle")
public class LifecycleController {

    private final LifecycleService service;

    public LifecycleController(LifecycleService service) {
        this.service = service;
    }

    @GetMapping("/questions/{id}")
    public Map<String, Object> questionState(@PathVariable Long id) {
        return Map.of("questionId", id, "status", service.stateOf(id));
    }

    @GetMapping("/operations/{operationId}")
    public ResponseEntity<Map<String, String>> operation(@PathVariable String operationId) {
        return service.operationStatus(operationId)
                .map(s -> ResponseEntity.ok(Map.of("operationId", operationId, "status", s)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("operationId", operationId, "status", "UNKNOWN")));
    }
}
