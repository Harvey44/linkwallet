package com.mobile.linkwallet.models;

import java.util.List;

public class SwaplistResp {

    private  boolean error;
    private String message;
    private  List<Swaps> result;

    public SwaplistResp(boolean error, String message, List<Swaps> result) {
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

    public List<Swaps> getResult() {
        return result;
    }
}

