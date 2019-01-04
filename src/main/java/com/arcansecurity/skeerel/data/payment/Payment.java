package com.arcansecurity.skeerel.data.payment;

import com.arcansecurity.skeerel.data.address.BaseAddress;
import com.arcansecurity.skeerel.util.json.JSONObject;

public final class Payment {

    private final String id;

    private final Boolean live;

    private final Long amount;

    private final Currency currency;

    private final Boolean captured;

    private final ReasonNotCaptured reasonNotCaptured;

    private final FraudRisk fraudRisk;

    private final BaseAddress billingAddress;

    private final Integer paymentErrorCode;

    private final String paymentErrorMessage;

    public Payment(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("payment object cannot be null");
        }

        id = json.optString("id", null);
        live = json.optBoolean("live", null);
        amount = json.optLong("amount", null);
        currency = Currency.fromString(json.optString("currency"));
        captured = json.optBoolean("captured", null);
        reasonNotCaptured = ReasonNotCaptured.fromString(json.optString("reason_not_captured"));
        fraudRisk = FraudRisk.fromString(json.optString("fraud_risk"));
        billingAddress = BaseAddress.build(json.optJSONObject("billing_address"));
        paymentErrorCode = json.optInt("payment_error_code", null);
        paymentErrorMessage = json.optString("id", null);
    }

    public String getId() {
        return id;
    }

    public Boolean getLive() {
        return live;
    }

    public Long getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public ReasonNotCaptured getReasonNotCaptured() {
        return reasonNotCaptured;
    }

    public FraudRisk getFraudRisk() {
        return fraudRisk;
    }

    public BaseAddress getBillingAddress() {
        return billingAddress;
    }

    public Integer getPaymentErrorCode() {
        return paymentErrorCode;
    }

    public String getPaymentErrorMessage() {
        return paymentErrorMessage;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", live=" + live +
                ", amount=" + amount +
                ", currency=" + currency +
                ", captured=" + captured +
                ", reasonNotCaptured=" + reasonNotCaptured +
                ", fraudRisk=" + fraudRisk +
                ", billingAddress=" + billingAddress +
                ", paymentErrorCode=" + paymentErrorCode +
                ", paymentErrorMessage='" + paymentErrorMessage + '\'' +
                '}';
    }
}
