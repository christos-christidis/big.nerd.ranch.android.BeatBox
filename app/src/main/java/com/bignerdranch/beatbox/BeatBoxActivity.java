package com.bignerdranch.beatbox;

import android.support.v4.app.Fragment;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
