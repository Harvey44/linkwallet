package com.mobile.linkwallet.models;

public class Stake {

    private String plan, duration, stake_amount, stake_return;

    public Stake(String plan, String duration, String stake_amount, String stake_return) {
        this.plan = plan;
        this.duration = duration;
        this.stake_amount = stake_amount;
        this.stake_return = stake_return;
    }

    public String getPlan() {
        return plan;
    }

    public String getDuration() {
        return duration;
    }

    public String getStake_amount() {
        return stake_amount;
    }

    public String getStake_return() {
        return stake_return;
    }
}
