package com.arcansecurity.skeerel.data.payment;

import com.arcansecurity.skeerel.data.address.BaseAddress;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.util.UUID;

public final class PaymentData {

    private UUID id;

    private Boolean live;

    private Long amount;

    private Currency currency;

    private Boolean captured;

    private BaseAddress billingAddress;

    private Integer paymentErrorCode;

    private String paymentErrorMessage;

    public PaymentData(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("Payment cannot be parsed due to incorrect data");
        }

        id = UUID.fromString(json.optString("id", null));
        live = json.optBoolean("live", null);
        amount = json.optLong("amount", null);
        currency = Currency.fromString(json.optString("currency", null));
        captured = json.optBoolean("captured", null);
        billingAddress = BaseAddress.build(json.optJSONObject("billing_address"));
        paymentErrorCode = json.optInt("payment_error_code", null);
        paymentErrorMessage = json.optString("payment_error_message", null);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public BaseAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BaseAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Integer getPaymentErrorCode() {
        return paymentErrorCode;
    }

    public void setPaymentErrorCode(Integer paymentErrorCode) {
        this.paymentErrorCode = paymentErrorCode;
    }

    public String getPaymentErrorMessage() {
        return paymentErrorMessage;
    }

    public void setPaymentErrorMessage(String paymentErrorMessage) {
        this.paymentErrorMessage = paymentErrorMessage;
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "id=" + id +
                ", live=" + live +
                ", amount=" + amount +
                ", currency=" + currency +
                ", captured=" + captured +
                ", billingAddress=" + billingAddress +
                ", paymentErrorCode=" + paymentErrorCode +
                ", paymentErrorMessage='" + paymentErrorMessage + '\'' +
                '}';
    }
}
