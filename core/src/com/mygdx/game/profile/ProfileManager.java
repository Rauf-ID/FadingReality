package com.mygdx.game.profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.observer.ProfileObserver;
import com.mygdx.game.observer.ProfileSubject;

import java.util.Enumeration;
import java.util.Hashtable;

public class ProfileManager extends ProfileSubject {

    private static ProfileManager profileManager;
    private static final String SAVEGAME_SUFFIX = ".json";
    public static final String DEFAULT_PROFILE = "default";

    private Json json;
    private PlayerConfig playerConfig = new PlayerConfig();
    private Hashtable<String,FileHandle> profiles = null;
    private String profileName;
    private boolean isNewProfile = false;

    private SettingsConfig settingsConfig = new SettingsConfig();
    private FileHandle fileSettings = Gdx.files.local("main/SETTINGS.json");


    private ProfileManager(){
        json = new Json();
        profiles = new Hashtable<String,FileHandle>();
        profiles.clear();
        profileName = DEFAULT_PROFILE;
        storeAllProfiles();
    }

    public static final ProfileManager getInstance(){
        if( profileManager == null){
            profileManager = new ProfileManager();
        }
        return profileManager;
    }

    public void setIsNewProfile(boolean isNewProfile){
        this.isNewProfile = isNewProfile;
    }

    public boolean getIsNewProfile(){
        return isNewProfile;
    }

    public Array<String> getProfileList(){
        Array<String> _profiles = new Array<String>();
        for (Enumeration<String> e = profiles.keys(); e.hasMoreElements();){
            _profiles.add(e.nextElement());
        }
        return _profiles;
    }

    public FileHandle getProfileFile(String profile){
        if( !doesProfileExist(profile) ){
            return null;
        }
        return profiles.get(profile);
    }

    public void storeAllProfiles(){
        if( Gdx.files.isLocalStorageAvailable() ){
            FileHandle[] files = Gdx.files.local("main/profile/.").list(SAVEGAME_SUFFIX);

            for(FileHandle file: files) {
                profiles.put(file.nameWithoutExtension(), file);
            }
        }else{
            //TODO: try external directory here
            return;
        }
    }

    public boolean doesProfileExist(String profileName){
        FileHandle fileHandle = Gdx.files.local("main/profile/" + profileName + SAVEGAME_SUFFIX);
        return fileHandle.exists();
    }

    public void writeProfileToStorage(String profileName,  String fileData, boolean overwrite) {
        String fullFilename = profileName+SAVEGAME_SUFFIX;

        boolean localFileExists = Gdx.files.local(fullFilename).exists();

        if( localFileExists && !overwrite ){
            return;
        }

        FileHandle file = null;
        if( Gdx.files.isLocalStorageAvailable() ) {
            file = Gdx.files.local("main/profile/" + fullFilename);
//            String encodedString = Base64Coder.encodeString(fileData);
//            file.writeString(encodedString, !overwrite);
            file.writeString(fileData, !overwrite);
        }

        profiles.put(profileName, file);
    }

    public void saveProfile(){
        notify(this, ProfileObserver.ProfileEvent.SAVING_PROFILE);
        String text = json.prettyPrint(json.toJson(playerConfig));
        writeProfileToStorage(profileName, text, true);
    }

    public void loadProfile(){
        if(isNewProfile){
            notify(this, ProfileObserver.ProfileEvent.CLEAR_CURRENT_PROFILE);
            saveProfile();
        }

        String fullProfileFileName = profileName + SAVEGAME_SUFFIX;
        boolean doesProfileFileExist = Gdx.files.local("main/profile/" + fullProfileFileName).exists();


        if(!doesProfileFileExist){
            return;
        }

        FileHandle encodedFile = profiles.get(profileName);
        String s = encodedFile.readString();
//        String decodedFile = Base64Coder.decodeString(s);

//        playerConfig = json.fromJson(PlayerConfig.class, decodedFile);
        playerConfig = json.fromJson(PlayerConfig.class, s);
        notify(this, ProfileObserver.ProfileEvent.PROFILE_LOADED);
        isNewProfile = false;
    }

    public void loadSetting() {
        String settings = fileSettings.readString();
        settingsConfig = json.fromJson(SettingsConfig.class, settings);
    }

    public void saveSetting() {
        String text = json.prettyPrint(json.toJson(settingsConfig));
        fileSettings.writeString(text, false);
    }

    public void setCurrentProfile(String profileName){
        if( doesProfileExist(profileName) ){
            this.profileName = profileName;
        }else{
            this.profileName = DEFAULT_PROFILE;
        }
    }


    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
