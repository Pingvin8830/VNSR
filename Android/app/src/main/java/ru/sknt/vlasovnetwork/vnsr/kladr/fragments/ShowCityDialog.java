package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;

public class ShowCityDialog extends ShowObjectDialog {
    private final City mCity;
    private TextView mTxtName;
    private TextView mTxtType;

    public ShowCityDialog (City city) {
        mCity = city;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_show_city;
    }

    @Override
    protected void getDataViews() {
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtType = mDialogView.findViewById(R.id.txtType);
    }

    @Override
    protected void setData() {
        mTxtName.setText(mCity.getName());
        mTxtType.setText(mCity.getCityType().getName());
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_city);
    }

    @Override
    protected void deleteObject() {
        CityesFragment callingFragment = (CityesFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("cityes");
        assert callingFragment != null;
        callingFragment.deleteCity(mCity);
    }
}