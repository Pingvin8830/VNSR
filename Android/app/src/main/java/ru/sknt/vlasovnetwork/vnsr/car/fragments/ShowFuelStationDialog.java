package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;

public class ShowFuelStationDialog extends ShowObjectDialog {
    private final FuelStation mFuelStation;
    private TextView mTxtCompany;
    private TextView mTxtName;
    private TextView mTxtPhone;
    private TextView mTxtAddress;


    public ShowFuelStationDialog(FuelStation fuelStation) {
        mFuelStation = fuelStation;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.car_show_fuel_station;
    }

    @Override
    protected void getDataViews() {
        mTxtCompany = mDialogView.findViewById(R.id.txtCompany);
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtPhone = mDialogView.findViewById(R.id.txtPhone);
        mTxtAddress = mDialogView.findViewById(R.id.txtAddress);
    }

    @Override
    protected void setData() {
        mTxtCompany.setText(mFuelStation.getCompany());
        mTxtName.setText(mFuelStation.getName());
        mTxtPhone.setText(mFuelStation.getPhone());
        mTxtAddress.setText(mFuelStation.getAddress().toString());
    }

    @Override
    protected String getDialogMessageText() {
        return "Your fuel station";
    }

    @Override
    protected void deleteObject() {
        FuelStationsFragment callingFragment = (FuelStationsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fuel_stations");
        callingFragment.deleteFuelStation(mFuelStation);
    }
}