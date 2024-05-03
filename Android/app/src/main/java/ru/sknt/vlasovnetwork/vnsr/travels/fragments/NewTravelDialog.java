package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.DatePickerFragment;
import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class NewTravelDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private Spinner mSpnState;
    private EditText mEdtxtParticipants;
    private EditText mEdtxtFuelConsumption;
    private EditText mEdtxtFuelPrice;
    private Button mBttnStartDateTime;
    private Button mBttnEndDateTime;
    private List<String> mStates;
    private List<String> mStateCodes;
    private String mName;
    private String mState;
    private String mParticipants;
    private float mFuelConsumption;
    private float mFuelPrice;
    private FormatedDate mStartDateTime;
    private FormatedDate mEndDateTime;

    @Override
    protected void setObjectsLists() {
        mStates = new ArrayList<>();
        mStateCodes = new ArrayList<>();
        for (String[] stateList : Travel.STATES) {
            if (stateList[0].equals("U")) { continue; }
            mStateCodes.add(stateList[0]);
            mStates.add(stateList[1]);
        }
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_travel; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnState = mDialogView.findViewById(R.id.spnState);
        mEdtxtParticipants = mDialogView.findViewById(R.id.edtxtParticipants);
        mEdtxtFuelConsumption = mDialogView.findViewById(R.id.edtxtFuelConsumption);
        mEdtxtFuelPrice = mDialogView.findViewById(R.id.edtxtFuelPrice);
        mBttnStartDateTime = mDialogView.findViewById(R.id.bttnStartDateTime);
        mBttnEndDateTime = mDialogView.findViewById(R.id.bttnEndDateTime);

        mBttnStartDateTime.setOnClickListener(this);
        mBttnEndDateTime.setOnClickListener(this);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mStates);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnState.setAdapter(stateAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_travel); }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mState = mStateCodes.get(mSpnState.getSelectedItemPosition());
        mParticipants = mEdtxtParticipants.getText().toString();
        try { mFuelConsumption = Float.parseFloat(mEdtxtFuelConsumption.getText().toString()); }
        catch (NumberFormatException e) { mFuelConsumption = 0f; }
        try { mFuelPrice = Float.parseFloat(mEdtxtFuelPrice.getText().toString()); }
        catch (NumberFormatException e) { mFuelPrice = 0f; }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        else if (mState.equals("U")) { error = getResources().getString(R.string.err_bad_state); }
        else if (mParticipants.isEmpty()) { error = getResources().getString(R.string.err_bad_participants); }
        else if (mFuelConsumption < 0.1) { error = getResources().getString(R.string.err_bad_fuel_consumption); }
        else if (mFuelPrice < 0.01) { error = getResources().getString(R.string.err_bad_fuel_price); }
        else if (mStartDateTime == null) { error = getResources().getString(R.string.err_bad_start_date_time); }
        else if (mEndDateTime == null) { error = getResources().getString(R.string.err_bad_end_date_time); }
        return error;
    }

    @Override
    protected void createObject() {
        Travel newTravel = new Travel(mName, mParticipants, mState, mFuelConsumption, mFuelPrice, mStartDateTime, mEndDateTime);
        TravelsFragment callingFragment = (TravelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("travels");
        assert callingFragment != null;
        callingFragment.createNewTravel(newTravel);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.bttnStartDateTime) {
            new DatePickerFragment("new_travel_start").show(requireActivity().getSupportFragmentManager(), "datePicker");
        } else if (view.getId() == R.id.bttnEndDateTime) {
            new DatePickerFragment("new_travel_end").show(requireActivity().getSupportFragmentManager(), "datePicker");
        }
    }
    public void setStartDateTime(FormatedDate dateTime) {
        mStartDateTime = dateTime;
        mBttnStartDateTime.setText(mStartDateTime.toString());
    }
    public void setEndDateTime(FormatedDate dateTime) {
        mEndDateTime = dateTime;
        mBttnEndDateTime.setText(mEndDateTime.toString());
    }
}
