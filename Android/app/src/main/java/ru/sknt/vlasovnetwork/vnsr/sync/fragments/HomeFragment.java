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
import ru.sknt.vlasovnetwork.vnsr.car.CarActivity;
import ru.sknt.vlasovnetwork.vnsr.sync.SyncActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtxtUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sync_home, container, false);
        mEdtxtUrl = view.findViewById(R.id.edtxtUrl);
        Button bttnChange = view.findViewById(R.id.bttnChange);

        if (SyncActivity.getUrl() != null) { mEdtxtUrl.setText(SyncActivity.getUrl()); }
        bttnChange.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bttnChange) {
            SyncActivity.setUrl(mEdtxtUrl.getText().toString());
            Toast.makeText(getContext(), "Url changed", Toast.LENGTH_SHORT).show();
        }
    }
}
