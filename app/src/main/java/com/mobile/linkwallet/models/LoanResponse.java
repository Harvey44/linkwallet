package com.mobile.linkwallet.models;

import java.util.List;

public class LoanResponse {
    private String message;
    private boolean error;
    private String all_requests;
    private List<Loan> result;

    public LoanResponse(String message, boolean error, String all_requests, List<Loan> result) {
        this.message = message;
        this.error = error;
        this.all_requests = all_requests;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public String getAll_requests() {
        return all_requests;
    }

    public List<Loan> getResult() {
        return result;
    }
}
