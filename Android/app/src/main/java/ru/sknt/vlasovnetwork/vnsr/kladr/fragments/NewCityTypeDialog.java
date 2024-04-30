package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.EditText;

import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class NewCityTypeDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private EditText mEdtxtShort;
    private String mName;
    private String mShort;

    @Override
    protected void setObjectsLists() {}

    @Override
    protected int getLayoutCode() { return R.layout.kladr_new_city_type; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mEdtxtShort = mDialogView.findViewById(R.id.edtxtShort);
    }

    @Override
    protected void setAdapters() {}

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_city_type); }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mShort = mEdtxtShort.getText().toString();
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if ((mShort.length() > 5) || (mShort.isEmpty())) { error = getResources().getString(R.string.err_bad_short); }
        else if (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        return error;
    }

    @Override
    protected void createObject() {
        CityType newCityType = new CityType(mName, mShort);
        CityTypesFragment callingFragment = (CityTypesFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("city_types");
        assert callingFragment != null;
        callingFragment.createNewCityType(newCityType);
    }
}