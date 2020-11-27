package com.mobile.linkwallet.models;

import java.util.List;

public class SwaplistResponse {

    private boolean error;
    private String message;
    private List<Swaplist> result_from;
    private List<Swaplist> result_to;
    private String errorMessage;

    public SwaplistResponse(boolean error, String message, List<Swaplist> result_from, List<Swaplist> result_to, String errorMessage) {
        this.error = error;
        this.message = message;
        this.result_from = result_from;
        this.result_to = result_to;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<Swaplist> getResult_from() {
        return result_from;
    }

    public List<Swaplist> getResult_to() {
        return result_to;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
