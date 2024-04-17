package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class ShowCityTypeDialog extends ShowObjectDialog {
    private final CityType mCityType;
    private TextView mTxtName;
    private TextView mTxtShort;

    public ShowCityTypeDialog (CityType cityType) {
        mCityType = cityType;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_show_city_type;
    }

    @Override
    protected void getDataViews() {
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtShort = mDialogView.findViewById(R.id.txtShort);
    }

    @Override
    protected void setData() {
        mTxtName.setText(mCityType.getName());
        mTxtShort.setText(mCityType.getShort());
    }

    @Override
    protected String getDialogMessageText() {
        return "Your city type";
    }

    @Override
    protected void deleteObject() {
        CityTypesFragment callingFragment = (CityTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("city_types");
        callingFragment.deleteCityType(mCityType);
    }
}