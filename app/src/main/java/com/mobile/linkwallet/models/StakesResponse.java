package com.mobile.linkwallet.models;

import java.util.List;

public class StakesResponse {

    private  boolean error;
    private String message;
    private List<Stakes> result;

    public StakesResponse(boolean error, String message, List<Stakes> result) {
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

    public List<Stakes> getResult() {
        return result;
    }
}
