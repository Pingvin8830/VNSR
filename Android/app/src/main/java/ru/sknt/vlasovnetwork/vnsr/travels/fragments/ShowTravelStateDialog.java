package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;

public class ShowTravelStateDialog extends ShowObjectDialog {
    private final TravelState mTravelState;
    private TextView mTxtName;

    public ShowTravelStateDialog(TravelState travelState) {
        mTravelState = travelState;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.travels_show_travel_state;
    }

    @Override
    protected void getDataViews() { mTxtName = mDialogView.findViewById(R.id.txtName); }

    @Override
    protected void setData() { mTxtName.setText(mTravelState.getName()); }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_travel_state);
    }

    @Override
    protected void deleteObject() {
        TravelStatesFragment callingFragment = (TravelStatesFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("travel_states");
        assert callingFragment != null;
        callingFragment.deleteTravelState(mTravelState);
    }
}