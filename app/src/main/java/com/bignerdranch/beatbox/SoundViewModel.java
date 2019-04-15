package com.bignerdranch.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

// SOS: AFAICT anything referenced in the layout must be public, eg here the class and getTitle.
public class SoundViewModel extends BaseObservable {

    private Sound mSound;
    private BeatBox mBeatBox;

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
        // SOS: notifies binding class that this field changed so that it will run the @{} code in
        // the layout again. To refresh ALL @Bindable fields of the view-model, use notifyChange();
        notifyPropertyChanged(BR.title);
    }
}
