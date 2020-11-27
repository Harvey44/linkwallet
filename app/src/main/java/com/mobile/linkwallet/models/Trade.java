package com.mobile.linkwallet.models;

public class Trade {
    private String asset_volume, quoted_price, status, color, asset, request_date;

    public Trade(String asset_volume, String quoted_price, String status, String color, String asset, String request_date) {
        this.asset_volume = asset_volume;
        this.quoted_price = quoted_price;
        this.status = status;
        this.color = color;
        this.asset = asset;
        this.request_date = request_date;
    }

    public String getAsset_volume() {
        return asset_volume;
    }

    public String getQuoted_price() {
        return quoted_price;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getAsset() {
        return asset;
    }

    public String getRequest_date() {
        return request_date;
    }
}
