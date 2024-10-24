package com.kors.parser.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PaymentService {

    public String getConfirmationToken(double value) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/sendPayment?value=" + value;
        String test = restTemplate.getForObject(apiUrl, String.class);
        return test;
    }
}
