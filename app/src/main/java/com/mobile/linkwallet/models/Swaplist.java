package com.mobile.linkwallet.models;

public class Swaplist {
    private String id, name, balance_fiat_symbol;;

    public Swaplist(String id, String name, String balance_fiat_symbol) {
        this.id = id;
        this.name = name;
        this.balance_fiat_symbol = balance_fiat_symbol;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBalance_fiat_symbol() {
        return balance_fiat_symbol;
    }
}
