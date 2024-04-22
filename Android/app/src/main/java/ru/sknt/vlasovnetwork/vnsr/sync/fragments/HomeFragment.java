package ru.sknt.vlasovnetwork.vnsr.sync.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.sync.SyncActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtxtServerUrl;
    private SyncActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (SyncActivity) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sync_home, container, false);
        mEdtxtServerUrl = view.findViewById(R.id.edtxtServerUrl);
        Button bttnChange = view.findViewById(R.id.bttnChange);

        mEdtxtServerUrl.setText(mActivity.getServerUrl());

        bttnChange.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bttnChange) {
            mActivity.setServerUrl(mEdtxtServerUrl.getText().toString());
            Toast.makeText(getContext(), "Settings changed", Toast.LENGTH_SHORT).show();
        }
    }
}
