package com.kors.parser.model;

public class PaymentConfirmation {
    private String type;

    public PaymentConfirmation() {
    }

    public PaymentConfirmation(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
