package co.edu.unicauca.saberpro.question.infrastructure.client;

import co.edu.unicauca.saberpro.question.application.ValidationServiceUnavailableException;
import co.edu.unicauca.saberpro.question.domain.model.Question;
import co.edu.unicauca.saberpro.question.domain.port.QuestionValidationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/** Invocacion sincrona HTTP REST: POST {validation.service.url}/validate */
@Component
public class ValidationRestClient implements QuestionValidationPort {

    private final RestTemplate restTemplate;
    private final String validationUrl;

    public ValidationRestClient(RestTemplate restTemplate,
                                @Value("${validation.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.validationUrl = baseUrl + "/validate";
    }

    @Override
    public ValidationResult validate(Question question) {
        ValidationRequest request = new ValidationRequest(
                question.getStatement(),
                question.getOptions() == null ? null
                        : question.getOptions().stream()
                            .map(o -> new OptionPayload(o.key(), o.text())).toList(),
                question.getCorrectKey(),
                question.getJustification());
        try {
            ResponseEntity<ValidationResponse> response =
                    restTemplate.postForEntity(validationUrl, request, ValidationResponse.class);
            ValidationResponse body = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && (body == null || body.valid())) {
                return new ValidationResult(true, List.of());
            }
            return new ValidationResult(false, body == null ? List.of() : body.errors());
        } catch (HttpClientErrorException.BadRequest e) {
            // 400 del Validation Service: se propaga la lista de errores
            ValidationResponse body = e.getResponseBodyAs(ValidationResponse.class);
            return new ValidationResult(false, body == null || body.errors() == null
                    ? List.of("La pregunta no supero la validacion estructural") : body.errors());
        } catch (RestClientException e) {
            throw new ValidationServiceUnavailableException(
                    "No fue posible comunicarse con el Validation Service en " + validationUrl, e);
        }
    }

    record OptionPayload(String key, String text) {
    }

    record ValidationRequest(String statement, List<OptionPayload> options,
                             String correctKey, String justification) {
    }

    record ValidationResponse(boolean valid, List<String> errors) {
    }
}
