package com.mobile.linkwallet.models;

import java.util.List;

public class StakeResp {

    private  boolean error;
    private String message;
    private List<Plans> plan_result;
    private List<Swaplist> wallet_result;

    public StakeResp(boolean error, String message, List<Plans> plan_result, List<Swaplist> wallet_result) {
        this.error = error;
        this.message = message;
        this.plan_result = plan_result;
        this.wallet_result = wallet_result;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<Plans> getPlan_result() {
        return plan_result;
    }

    public List<Swaplist> getWallet_result() {
        return wallet_result;
    }
}
