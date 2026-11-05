package co.edu.unicauca.saberpro.validation.service;

import co.edu.unicauca.saberpro.validation.dto.OptionDto;
import co.edu.unicauca.saberpro.validation.dto.ValidationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Reglas de validacion estructural (HU03):
 * exactamente 4 opciones, clave correcta existente y justificacion valida.
 */
@Service
public class QuestionValidator {

    public static final int REQUIRED_OPTIONS = 4;
    public static final int MIN_JUSTIFICATION_LENGTH = 20;

    public List<String> validate(ValidationRequest request) {
        List<String> errors = new ArrayList<>();
        if (request == null) {
            errors.add("El cuerpo de la solicitud es obligatorio.");
            return errors;
        }

        if (isBlank(request.statement())) {
            errors.add("El enunciado de la pregunta es obligatorio.");
        }

        List<OptionDto> options = request.options() == null ? List.of() : request.options();
        if (options.size() != REQUIRED_OPTIONS) {
            errors.add("La pregunta debe tener exactamente " + REQUIRED_OPTIONS
                    + " opciones (se recibieron " + options.size() + ").");
        }

        Set<String> keys = new HashSet<>();
        boolean incompleteReported = false;
        for (OptionDto option : options) {
            if (option == null || isBlank(option.key()) || isBlank(option.text())) {
                if (!incompleteReported) {
                    errors.add("Cada opcion debe tener clave y texto.");
                    incompleteReported = true;
                }
            } else if (!keys.add(normalize(option.key()))) {
                errors.add("La clave de opcion '" + option.key().trim() + "' esta repetida.");
            }
        }

        if (isBlank(request.correctKey())) {
            errors.add("La clave correcta es obligatoria.");
        } else if (!keys.contains(normalize(request.correctKey()))) {
            errors.add("La clave correcta '" + request.correctKey().trim()
                    + "' no corresponde a ninguna de las opciones.");
        }

        if (isBlank(request.justification())) {
            errors.add("La justificacion es obligatoria.");
        } else if (request.justification().trim().length() < MIN_JUSTIFICATION_LENGTH) {
            errors.add("La justificacion debe tener al menos " + MIN_JUSTIFICATION_LENGTH + " caracteres.");
        }

        return errors;
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase();
    }
}
