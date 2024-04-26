package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;

public class NewTravelDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private Spinner mSpnTravelState;
    private EditText mEdtxtParticipants;
    private EditText mEdtxtFuelConsumption;
    private EditText mEdtxtFuelPrice;
    private List<TravelState> mTravelStates;
    private String mName;
    private TravelState mTravelState;
    private String mParticipants;
    private float mFuelConsumption;
    private float mFuelPrice;

    @Override
    protected void setObjectsLists() { mTravelStates = MainActivity.TravelStateDao.getAll(); }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_travel; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnTravelState = mDialogView.findViewById(R.id.spnTravelState);
        mEdtxtParticipants = mDialogView.findViewById(R.id.edtxtParticipants);
        mEdtxtFuelConsumption = mDialogView.findViewById(R.id.edtxtFuelConsumption);
        mEdtxtFuelPrice = mDialogView.findViewById(R.id.edtxtFuelPrice);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<TravelState> travelStateAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mTravelStates);
        travelStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnTravelState.setAdapter(travelStateAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_travel); }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mTravelState = mTravelStates.get(mSpnTravelState.getSelectedItemPosition());
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
        else if (mParticipants.isEmpty()) { error = getResources().getString(R.string.err_bad_participants); }
        else if (mFuelConsumption < 0.1) { error = getResources().getString(R.string.err_bad_fuel_consumption); }
        else if (mFuelPrice < 0.01) { error = getResources().getString(R.string.err_bad_fuel_price); }
        return error;
    }

    @Override
    protected void createObject() {
        Travel newTravel = new Travel(mName, mParticipants, mTravelState, mFuelConsumption, mFuelPrice);
        TravelsFragment callingFragment = (TravelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("travels");
        assert callingFragment != null;
        callingFragment.createNewTravel(newTravel);
    }
}
