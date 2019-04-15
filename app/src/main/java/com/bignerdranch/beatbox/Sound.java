package com.bignerdranch.beatbox;

class Sound {

    private String mAssetPath;
    private String mName;

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
}
