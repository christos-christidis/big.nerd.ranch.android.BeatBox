package com.bignerdranch.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BeatBox {

    private static final String LOG_TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();

    BeatBox(Context context) {
        mAssets = context.getAssets();
        loadSounds();
    }

    private void loadSounds() {
        String[] filenames;
        try {
            filenames = mAssets.list(SOUNDS_FOLDER);
            Objects.requireNonNull(filenames);
            Log.i(LOG_TAG, "Found " + filenames.length + " sounds");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not list assets", e);
            return;
        }

        for (String filename : filenames) {
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }
    }

    List<Sound> getSounds() {
        return mSounds;
    }
}
