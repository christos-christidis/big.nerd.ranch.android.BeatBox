package com.bignerdranch.beatbox;

import android.support.v4.app.Fragment;

// SOS: in MVC we had the Model classes which represented the world (plus databases), the View which
// was the layout and finally, the Controller (fragments) which handled EVERYTHING, eg CrimeFragment
// in CriminalIntent gets handles to the views, then the data from db and displays that data in the
// views. Moreover, it does "view" things like formatting Dates, null-checking of values before it
// passes them to the views etc. In MVVM, formatting is done in the layout thanks to addition of
// simple lambda expressions, null-coalescing etc (see page 397) and/or in the ViewModel.
public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
