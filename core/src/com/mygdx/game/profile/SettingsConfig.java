package com.mygdx.game.profile;

public class SettingsConfig {

    private String lastActiveAccount;
    private String language;
    private float musicVolume;
    private float sfxVolume;

    SettingsConfig() {}

    public String getLastActiveAccount() {
        return lastActiveAccount;
    }

    public void setLastActiveAccount(String lastActiveAccount) {
        this.lastActiveAccount = lastActiveAccount;
    }
}
