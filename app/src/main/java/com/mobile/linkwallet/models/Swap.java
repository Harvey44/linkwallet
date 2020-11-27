package com.mobile.linkwallet.models;

public class Swap {

    private String swap_amount, swap_equivalent, swap_fee, fiat_amount_usd, fiat_amount, status;

    public Swap(String swap_amount, String swap_equivalent, String swap_fee, String fiat_amount_usd, String fiat_amount, String status) {
        this.swap_amount = swap_amount;
        this.swap_equivalent = swap_equivalent;
        this.swap_fee = swap_fee;
        this.fiat_amount_usd = fiat_amount_usd;
        this.fiat_amount = fiat_amount;
        this.status = status;
    }

    public String getSwap_amount() {
        return swap_amount;
    }

    public String getSwap_equivalent() {
        return swap_equivalent;
    }

    public String getSwap_fee() {
        return swap_fee;
    }

    public String getFiat_amount_usd() {
        return fiat_amount_usd;
    }

    public String getFiat_amount() {
        return fiat_amount;
    }

    public String getStatus() {
        return status;
    }
}
