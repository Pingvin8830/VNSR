package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.sknt.vlasovnetwork.vnsr.R;

public class FuelsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_fuels, container, false);
        return v;
    }
}