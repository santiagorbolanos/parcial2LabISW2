package co.edu.unicauca.saberpro.validation.dto;

import java.util.List;

public record ValidationResponse(boolean valid, List<String> errors) {
}
