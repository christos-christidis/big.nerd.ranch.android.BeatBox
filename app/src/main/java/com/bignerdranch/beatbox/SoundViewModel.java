package com.bignerdranch.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class SoundViewModel extends BaseObservable {

    private Sound mSound;
    private final BeatBox mBeatBox;

    SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    Sound getSound() {
        return mSound;
    }

    @Bindable
    public String getTitle() {
        return mSound.getName();
    }

    void setSound(Sound sound) {
        mSound = sound;
        notifyPropertyChanged(BR.title);
    }

    public void onButtonClicked() {
        mBeatBox.play(mSound);
    }
}
