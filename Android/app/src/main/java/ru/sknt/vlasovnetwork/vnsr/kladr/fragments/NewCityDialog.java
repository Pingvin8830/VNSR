package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class NewCityDialog extends NewObjectDialog {
    private List<CityType> mCityTypes;
    private EditText mEdtxtName;
    private Spinner mSpnCityType;
    private String mName;
    private CityType mCityType;

    @Override
    protected void setObjectsLists() {
        mCityTypes = MainActivity.CityTypeDao.getAll();
    }

    @Override
    protected int getLayoutCode() { return R.layout.kladr_new_city; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnCityType = mDialogView.findViewById(R.id.spnType);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<CityType> cityTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mCityTypes);
        cityTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnCityType.setAdapter(cityTypeAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_city); }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mCityType = mCityTypes.get(mSpnCityType.getSelectedItemPosition());
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        return error;
    }

    @Override
    protected void createObject() {
        City newCity = new City(mName, mCityType);
        CityesFragment callingFragment = (CityesFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("cityes");
        assert callingFragment != null;
        callingFragment.createNewCity(newCity);
    }
}