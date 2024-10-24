package com.kors.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest {
    @JsonProperty("amount")
    private PaymentAmount paymentAmount;
    @JsonProperty("confirmation")
    private PaymentConfirmation paymentConfirmation;
    private Boolean capture;
    private String description;

    public PaymentRequest(PaymentAmount paymentAmount, PaymentConfirmation paymentConfirmation, Boolean capture, String description) {
        this.paymentAmount = paymentAmount;
        this.paymentConfirmation = paymentConfirmation;
        this.capture = capture;
        this.description = description;
    }

    public PaymentAmount getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(PaymentAmount paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentConfirmation getPaymentConfirmation() {
        return paymentConfirmation;
    }

    public void setPaymentConfirmation(PaymentConfirmation paymentConfirmation) {
        this.paymentConfirmation = paymentConfirmation;
    }

    public Boolean getCapture() {
        return capture;
    }

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
