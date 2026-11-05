package co.edu.unicauca.saberpro.validation.web;

import co.edu.unicauca.saberpro.validation.dto.ValidationRequest;
import co.edu.unicauca.saberpro.validation.dto.ValidationResponse;
import co.edu.unicauca.saberpro.validation.service.QuestionValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ValidationController {

    private final QuestionValidator validator;

    public ValidationController(QuestionValidator validator) {
        this.validator = validator;
    }

    /** 200 OK si la pregunta es valida; 400 Bad Request con la lista de errores en caso contrario. */
    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validate(@RequestBody ValidationRequest request) {
        List<String> errors = validator.validate(request);
        if (errors.isEmpty()) {
            return ResponseEntity.ok(new ValidationResponse(true, List.of()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationResponse(false, errors));
    }
}
