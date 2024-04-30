package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.sknt.vlasovnetwork.vnsr.R;

public class ToolroadsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.travels_toolroads, container, false);
        return v;
    }
}
