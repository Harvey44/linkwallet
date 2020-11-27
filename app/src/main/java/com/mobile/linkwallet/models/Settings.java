package com.mobile.linkwallet.models;

import com.google.gson.annotations.SerializedName;

public class Settings {
    @SerializedName("2fa")
    private boolean gcode;

    private boolean backup_phrase;

    private boolean email_verified;

    private String currency;

    public Settings(boolean gcode, boolean backup_phrase, boolean email_verified, String currency) {
        this.gcode = gcode;
        this.backup_phrase = backup_phrase;
        this.email_verified = email_verified;
        this.currency = currency;
    }

    public boolean isGcode() {
        return gcode;
    }

    public boolean isBackup_phrase() {
        return backup_phrase;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }
    public String getCurrency() {
        return currency;
    }
}
