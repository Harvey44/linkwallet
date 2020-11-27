package com.mobile.linkwallet.models;

public class Stakes {

    private String id, stake_amount, stake_profit, status, color, start_date, end_date;

    public Stakes(String id, String stake_amount, String stake_profit, String status, String color, String start_date, String end_date) {
        this.id = id;
        this.stake_amount = stake_amount;
        this.stake_profit = stake_profit;
        this.status = status;
        this.color = color;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public String getStake_amount() {
        return stake_amount;
    }

    public String getStake_profit() {
        return stake_profit;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
