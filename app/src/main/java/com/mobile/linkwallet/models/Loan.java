package com.mobile.linkwallet.models;

public class Loan {
    private String amount, portfolio_size, status, color, request_date;

    public Loan(String amount, String portfolio_size, String status, String color, String request_date) {
        this.amount = amount;
        this.portfolio_size = portfolio_size;
        this.status = status;
        this.color = color;
        this.request_date = request_date;
    }

    public String getAmount() {
        return amount;
    }

    public String getPortfolio_size() {
        return portfolio_size;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getRequest_date() {
        return request_date;
    }
}
