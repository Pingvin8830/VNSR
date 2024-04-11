package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.sknt.vlasovnetwork.vnsr.R;

public class RegionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        View v = inflater.inflate(R.layout.kladr_regions, container, false);

        return v;
    }
}
