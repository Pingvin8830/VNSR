package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.EditText;


import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;

public class NewTravelStateDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private String mName;

    @Override
    protected void setObjectsLists() {}

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_travel_state; }

    @Override
    protected void getDataViews() { mEdtxtName = mDialogView.findViewById(R.id.edtxtName); }

    @Override
    protected void setAdapters() {}

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_travel_state); }

    @Override
    protected void setData() { mName = mEdtxtName.getText().toString(); }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        return error;
    }

    @Override
    protected void createObject() {
        TravelState newTravelState = new TravelState(mName);
        TravelStatesFragment callingFragment = (TravelStatesFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("travel_states");
        assert callingFragment != null;
        callingFragment.createNewTravelState(newTravelState);
    }
}
