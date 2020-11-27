package com.mobile.linkwallet.models;

public class StakeResponse {

    private boolean error;
    private String message;
    private Stake result;

    public StakeResponse(boolean error, String message, Stake result) {
        this.error = error;
        this.message = message;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Stake getResult() {
        return result;
    }
}
