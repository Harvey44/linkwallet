package com.mobile.linkwallet.models;

import com.google.gson.annotations.SerializedName;

public class SwapResp {
    private boolean error;
    private String message;
    private Swap result;

    public SwapResp(boolean error, String message, Swap result) {
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

    public Swap getResult() {
        return result;
    }
}
