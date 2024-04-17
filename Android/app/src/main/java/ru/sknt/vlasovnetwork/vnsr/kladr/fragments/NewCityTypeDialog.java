package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.EditText;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class NewCityTypeDialog extends NewObjectDialog {
    private List<CityType> mCityTypes;
    private EditText mEdtxtName;
    private EditText mEdtxtShort;
    private String mName;
    private String mShort;

    @Override
    protected void setObjectsLists() { mCityTypes = MainActivity.CityTypeDao.getAll(); }

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
    protected String getDialogMessageText() { return "Add a new city type"; }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mShort = mEdtxtShort.getText().toString();
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if ((mShort.length() > 3) || (mShort.isEmpty())) {
            error = "Bad short name";
        } else if (mName.isEmpty()) {
            error = "Bad name";
        }
        return error;
    }

    @Override
    protected void createObject() {
        CityType newCityType = new CityType(mName, mShort);
        CityTypesFragment callingFragment = (CityTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("city_types");
        callingFragment.createNewCityType(newCityType);
    }
}