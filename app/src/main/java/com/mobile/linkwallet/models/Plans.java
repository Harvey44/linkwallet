package com.mobile.linkwallet.models;

public class Plans {

    private String id, name, duration, percentage_cent;

    public Plans(String id, String name, String duration, String percentage_cent) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.percentage_cent = percentage_cent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getPercentage_cent() {
        return percentage_cent;
    }
}
