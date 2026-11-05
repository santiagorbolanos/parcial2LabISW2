# Parcial 2 (Corte II) - LISW2 - Banco de Preguntas Saber PRO

Tres microservicios Spring Boot 3.2 / Java 17:

| Servicio | Puerto | Rol |
|---|---|---|
| validation-service | 8082 | `POST /validate` (HU03): 4 opciones, clave existente, justificacion valida |
| question-service | 8083 | `POST /questions`, `GET /questions/{id}`, `POST /questions/{id}/submit` (H2 + JPA + RabbitMQ) |
| lifecycle-service | 8084 | Consume `question.submit.review.queue` y pasa la pregunta a `PENDING_REVIEW` |

## Como ejecutar

1. RabbitMQ: `docker compose up -d` (consola: http://localhost:15672, guest/guest).
   Sin Docker: instalar RabbitMQ local con el plugin de management.
2. En tres terminales (orden recomendado):
   ```
   cd validation-service && mvn spring-boot:run
   cd lifecycle-service  && mvn spring-boot:run
   cd question-service   && mvn spring-boot:run
   ```
3. Importar `postman/SaberPro_Parcial2.postman_collection.json` y ejecutar la coleccion (Runner).

## Mapa rubrica -> codigo

- **REST sincrono (30%)**: `question-service/.../infrastructure/client/ValidationRestClient.java` (RestTemplate),
  manejo 201/400 en `web/QuestionController` y `web/GlobalExceptionHandler`.
- **JPA + Arquitectura limpia (40%)**: `domain/model/Question` (Java puro), `infrastructure/jpa/record/QuestionRecord`
  (`@Entity`, `@Table(name="questions")`, `@OneToMany(cascade = ALL)` -> `AnswerOptionRecord`),
  `infrastructure/jpa/mapper/QuestionDomainMapper`, `infrastructure/jpa/QuestionRepositoryJpaImpl`
  (implementa `QuestionRepository` e inyecta `SpringQuestionDataRepository`, H2).
- **RabbitMQ (20%)**: `infrastructure/messaging/RabbitReviewCommandPublisher` (RabbitTemplate ->
  `saberpro.direct.exchange`, routing key `question.submit`), respuesta `202 Accepted` con `operationId` y
  `PROCESSING`; consumidor `@RabbitListener` en `lifecycle-service/.../messaging/SubmitQuestionListener`.
- **Pruebas (10%)**: coleccion Postman (201, 400, 202, estado en Lifecycle y cola en la API de management de RabbitMQ).

H2 console: http://localhost:8083/h2-console (JDBC URL `jdbc:h2:mem:questionsdb`, user `sa`, sin password).
