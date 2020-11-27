package com.mobile.linkwallet.models;

public class Swaps {
    String id, amount_from, amount_to, status, color, coin_from_code, coin_to_code, request_date;

    public Swaps(String id, String amount_from, String amount_to, String status, String color, String coin_from_code, String coin_to_code, String request_date) {
        this.id = id;
        this.amount_from = amount_from;
        this.amount_to = amount_to;
        this.status = status;
        this.color = color;
        this.coin_from_code = coin_from_code;
        this.coin_to_code = coin_to_code;
        this.request_date = request_date;
    }

    public String getId() {
        return id;
    }

    public String getAmount_from() {
        return amount_from;
    }

    public String getAmount_to() {
        return amount_to;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getCoin_from_code() {
        return coin_from_code;
    }

    public String getCoin_to_code() {
        return coin_to_code;
    }

    public String getRequest_date() {
        return request_date;
    }
}
