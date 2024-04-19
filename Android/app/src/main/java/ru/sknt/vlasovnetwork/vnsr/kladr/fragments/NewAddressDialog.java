package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class NewAddressDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private Spinner mSpnRegion;
    private Spinner mSpnCity;
    private Spinner mSpnStreet;
    private EditText mEdtxtHome;
    private EditText mEdtxtBuilding;
    private EditText mEdtxtFlat;
    private List<Region> mRegions;
    private List<City> mCityes;
    private List<Street> mStreets;
    private String mName;
    private Region mRegion;
    private City mCity;
    private Street mStreet;
    private String mHome;
    private String mBuilding;
    private int mFlat;

    @Override
    protected void setObjectsLists() {
        mRegions = MainActivity.RegionDao.getAll();
        mCityes = MainActivity.CityDao.getAll();
        mStreets = MainActivity.StreetDao.getAll();
    }

    @Override
    protected int getLayoutCode() { return R.layout.kladr_new_address; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnRegion = mDialogView.findViewById(R.id.spnRegion);
        mSpnCity = mDialogView.findViewById(R.id.spnCity);
        mSpnStreet = mDialogView.findViewById(R.id.spnStreet);
        mEdtxtHome = mDialogView.findViewById(R.id.edtxtHome);
        mEdtxtBuilding = mDialogView.findViewById(R.id.edtxtBuilding);
        mEdtxtFlat = mDialogView.findViewById(R.id.edtxtFlat);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Region> regionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mRegions);
        ArrayAdapter<City> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCityes);
        ArrayAdapter<Street> streetAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mStreets);

        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnRegion.setAdapter(regionAdapter);
        mSpnCity.setAdapter(cityAdapter);
        mSpnStreet.setAdapter(streetAdapter);
    }

    @Override
    protected String getDialogMessageText() { return "Add a new address"; }


    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mRegion = mRegions.get(mSpnRegion.getSelectedItemPosition());
        mCity = mCityes.get(mSpnCity.getSelectedItemPosition());
        mStreet = mStreets.get(mSpnStreet.getSelectedItemPosition());
        mHome = mEdtxtHome.getText().toString();
        mBuilding = mEdtxtBuilding.getText().toString();
        String flatString = mEdtxtFlat.getText().toString();
        if (flatString.isEmpty()) { mFlat = 0; }
        else { mFlat = Integer.parseInt(flatString); }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = "Bad name"; }
        else if (mHome.isEmpty()) { error = "Bad home"; }
        return error;
    }

    @Override
    protected void createObject() {
        Address newAddress = new Address(mName, mRegion, mCity, mStreet, mHome, mBuilding, mFlat);
        AddressesFragment callingFragment = (AddressesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("addresses");
        callingFragment.createNewAddress(newAddress);
    }
}
