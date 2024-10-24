package com.kors.parser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kors.parser.model.PaymentAmount;
import com.kors.parser.model.PaymentConfirmation;
import com.kors.parser.model.PaymentRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RestController
public class PaymentRESTController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/sendPayment")
    public String sendPayment(@RequestParam("value") double value) throws JsonProcessingException {
        String url = "https://api.yookassa.ru/v3/payments";
        // Создаем объект HttpHeaders для заголовков запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("480074", "test_j6CISFC63mcJCEwvijXNksXl76ujpQZMxNCM7lHApvI"); // Аутентификация
        headers.set("Idempotence-Key", UUID.randomUUID().toString()); // Генерируем ключ идемпотентности
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем объект PaymentRequest с данными для отправки
        PaymentRequest paymentRequest = new PaymentRequest(new PaymentAmount(value, "RUB"), new PaymentConfirmation("embedded"), true, "Заказ №10");

        // Создаем объект HttpEntity с телом запроса и заголовками
        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        // Отправляем POST запрос
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Обрабатываем ответ
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonObject = objectMapper.readValue(responseBody, Map.class);
            Map<String, String> confirmationMap = (Map<String, String>) jsonObject.get("confirmation");
            if (confirmationMap != null) {
                String confirmationToken = confirmationMap.get("confirmation_token");
                return confirmationToken;
            } else {
                return null;
            }
        }
        return null;
    }
}
