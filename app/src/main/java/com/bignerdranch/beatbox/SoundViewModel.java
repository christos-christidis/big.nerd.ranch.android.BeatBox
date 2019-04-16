package com.bignerdranch.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

// SOS: things referenced in the layout, eg the class itself, getTitle etc, must be public.
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
        // SOS: notify the binding class to run the code in @{viewModel.title} again. (if we want ALL
        // @Bindable fields to be refreshed, call notifyChange)
        notifyPropertyChanged(BR.title);
    }

    public void onButtonClicked() {
        mBeatBox.play(mSound);
    }
}
