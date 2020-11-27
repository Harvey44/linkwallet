package com.mobile.linkwallet.models;

import java.util.List;

public class TradeResponse {

    private boolean error;
    private String message, all_requests;
    private List<Trade> result;

    public TradeResponse(boolean error, String message, String all_requests, List<Trade> result) {
        this.error = error;
        this.message = message;
        this.all_requests = all_requests;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getAll_requests() {
        return all_requests;
    }

    public List<Trade> getResult() {
        return result;
    }
}
