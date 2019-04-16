package com.bignerdranch.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BeatBox {

    private static final String LOG_TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    BeatBox(Context context) {
        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1, 1, 1, 0, 1);
    }

    void release() {
        mSoundPool.release();
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
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Could not load sound " + filename, e);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    List<Sound> getSounds() {
        return mSounds;
    }
}
