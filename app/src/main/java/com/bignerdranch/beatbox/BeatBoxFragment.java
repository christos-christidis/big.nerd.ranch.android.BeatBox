package com.bignerdranch.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.beatbox.databinding.FragmentBeatBoxBinding;
import com.bignerdranch.beatbox.databinding.ListItemSoundBinding;

import java.util.List;
import java.util.Objects;

public class BeatBoxFragment extends Fragment {

    private BeatBox mBeatBox;

    public BeatBoxFragment() {
        // Required empty public constructor
    }

    static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity());
        mBeatBox = new BeatBox(getActivity());

        // SOS: if fragment is destroyed as normal on rotation, mBeatBox w its mSoundPool are also
        // destroyed & the sound is interrupted. W this method, onDestroy is not called (only onPause,
        // onStop, onDestroyView, onDetach and similarly onCreate is not called on creation. However,
        // I should NOT retain fragments if possible, cause 1) they're harder to debug, 2) if the
        // fragment has any handles to views, eg mTextView, these views contain refs to the old Context
        // even when a new one's been created, so the old one won't be gc'd. So remember to create
        // those handles in onCreateView and set them to null in onDestroyView.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    private class SoundViewHolder extends RecyclerView.ViewHolder {

        private ListItemSoundBinding mBinding;

        private SoundViewHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        // SOS: When we notify the binding class to re-run the layout code (see SOS in SoundViewModel),
        // that code is scheduled to be run at some point before the next frame. But think of what
        // happens when we're scrolling the recycler-view. It's possible that a new viewholder comes
        // into view and onBindViewHolder is called BEFORE we change the data. RecyclerView calculates
        // the dimensions immediately after onBindViewHolder, so it's very likely that it'll calculate
        // the wrong dimensions (for the old data). That leads to mysterious bugs, like:
        // https://www.e-gineering.com/2017/01/17/android-data-binding-subtleties/
        private void bind(Sound sound) {
            Objects.requireNonNull(mBinding.getViewModel());
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundViewHolder> {

        private List<Sound> mSounds;

        private SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @NonNull
        @Override
        public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound,
                    parent, false);
            return new SoundViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundViewHolder soundViewHolder, int position) {
            Sound sound = mSounds.get(position);
            soundViewHolder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
