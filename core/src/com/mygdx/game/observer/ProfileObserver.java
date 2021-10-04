package com.mygdx.game.observer;

import com.mygdx.game.profile.ProfileManager;

public interface ProfileObserver {
    public static enum ProfileEvent{
        PROFILE_LOADED,
        SAVING_PROFILE,
        CLEAR_CURRENT_PROFILE
    }

    void onNotify(final ProfileManager profileManager, ProfileEvent event);
}
