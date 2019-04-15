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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return binding.getRoot();
    }

    private class SoundViewHolder extends RecyclerView.ViewHolder {

        private ListItemSoundBinding mBinding;

        private SoundViewHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        // SOS: The best explanation for executePendingBindings: When we change binding data, the
        // change is scheduled for some point before the next frame. Reasonable, right? However, in
        // the mean-time, if a list is being scrolled down, a new view-holder appearing at the bottom
        // may have its onBindViewHolder called BEFORE the change happens. That's a problem, because
        // the recycler-view measures layout dimensions right after onBindViewHolder is called. So,
        // it'll get the wrong size (for the previous data) and that may result in bugs, eg
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
