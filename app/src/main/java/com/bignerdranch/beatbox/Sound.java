package com.bignerdranch.beatbox;

class Sound {

    private final String mAssetPath;
    private final String mName;
    private Integer mSoundId;

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
