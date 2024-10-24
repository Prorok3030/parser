package com.kors.parser.model;

public class PaymentAmount {
    private Double value;
    private String currency;

    public PaymentAmount() {
    }

    public PaymentAmount(Double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
