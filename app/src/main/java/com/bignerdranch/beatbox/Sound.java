package com.bignerdranch.beatbox;

class Sound {

    private String mAssetPath;
    private String mName;
    private Integer mSoundId;   // SOS: Integer makes it possible to have null here if no sound set.

    Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] pathComponents = assetPath.split("/");
        String filename = pathComponents[pathComponents.length-1];
        mName = filename.replace(".wav", "");
    }

    String getAssetPath() {
        return mAssetPath;
    }

    String getName() {
        return mName;
    }

    Integer getSoundId() {
        return mSoundId;
    }

    void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }

}
