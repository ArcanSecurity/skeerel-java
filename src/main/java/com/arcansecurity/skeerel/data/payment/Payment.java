package com.arcansecurity.skeerel.data.payment;

import com.arcansecurity.skeerel.util.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.UUID;

public final class Payment {

    private UUID id;

    private ZonedDateTime date;

    private UUID profileId;

    private Long amount;

    private Currency currency;

    private Status status;

    private Boolean live;

    private Boolean captured;

    private ZonedDateTime dateCaptured;

    private Boolean refunded;

    private ZonedDateTime dateRefunded;

    private Boolean reviewed;

    private ZonedDateTime dateReviewed;

    public Payment(JSONObject json) {
        if (null == json) {
            throw new IllegalArgumentException("payment object cannot be null");
        }

        id = UUID.fromString(json.optString("id", null));
        date = ZonedDateTime.parse(json.optString("date", null));
        profileId = UUID.fromString(json.optString("profile_id", null));
        amount = json.optLong("amount", null);
        currency = Currency.fromString(json.optString("currency", null));
        status = Status.fromString(json.optString("status", null));
        live = json.optBoolean("live", null);
        captured = json.optBoolean("captured", null);
        refunded = json.optBoolean("refunded", null);
        reviewed = json.optBoolean("reviewed", null);

        if (!json.isNull("date_captured")) {
            dateCaptured = ZonedDateTime.parse(json.optString("date_captured"));
        }

        if (!json.isNull("date_refunded")) {
            dateRefunded = ZonedDateTime.parse(json.optString("date_refunded"));
        }

        if (!json.isNull("date_reviewed")) {
            dateReviewed = ZonedDateTime.parse(json.optString("date_reviewed"));
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public ZonedDateTime getDateCaptured() {
        return dateCaptured;
    }

    public void setDateCaptured(ZonedDateTime dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public ZonedDateTime getDateRefunded() {
        return dateRefunded;
    }

    public void setDateRefunded(ZonedDateTime dateRefunded) {
        this.dateRefunded = dateRefunded;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    public ZonedDateTime getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(ZonedDateTime dateReviewed) {
        this.dateReviewed = dateReviewed;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date=" + date +
                ", profileId=" + profileId +
                ", amount=" + amount +
                ", currency=" + currency +
                ", status=" + status +
                ", live=" + live +
                ", captured=" + captured +
                ", dateCaptured=" + dateCaptured +
                ", refunded=" + refunded +
                ", dateRefunded=" + dateRefunded +
                ", reviewed=" + reviewed +
                ", dateReviewed=" + dateReviewed +
                '}';
    }
}
