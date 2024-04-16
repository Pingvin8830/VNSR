package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class NewAddressDialog extends DialogFragment implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtName;
    private Spinner mSpnRegion;
    private Spinner mSpnCity;
    private Spinner mSpnStreet;
    private EditText mEdtxtHome;
    private EditText mEdtxtBuilding;
    private EditText mEdtxtFlat;
    private final RegionDao mRegionDao;
    private final CityDao mCityDao;
    private final CityTypeDao mCityTypeDao;
    private final StreetDao mStreetDao;
    private final StreetTypeDao mStreetTypeDao;
    private final List<Region> mRegions;
    private final List<City> mCityes;
    private final List<Street> mStreets;

    public NewAddressDialog(RegionDao regionDao, CityDao cityDao, CityTypeDao cityTypeDao, StreetDao streetDao, StreetTypeDao streetTypeDao) {
        mRegionDao = regionDao;
        mCityDao = cityDao;
        mCityTypeDao = cityTypeDao;
        mStreetDao = streetDao;
        mStreetTypeDao = streetTypeDao;
        mRegions = mRegionDao.getAll();
        mCityes = mCityDao.getAll();
        mStreets = mStreetDao.getAll();
        for (City city : mCityes) { city.setCityType(mCityTypeDao); }
        for (Street street : mStreets) { street.setStreetType(mStreetTypeDao); }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAnimError = AnimationUtils.loadAnimation(getContext(),R.anim.error);
        mAnimError.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { mTxtError.setVisibility(View.VISIBLE); }
                    @Override
                    public void onAnimationEnd(Animation animation) { mTxtError.setVisibility(View.INVISIBLE); }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                }
        );
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_address, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        mSpnRegion = dialogView.findViewById(R.id.spnRegion);
        mSpnCity = dialogView.findViewById(R.id.spnCity);
        mSpnStreet = dialogView.findViewById(R.id.spnStreet);
        mEdtxtHome = dialogView.findViewById(R.id.edtxtHome);
        mEdtxtBuilding = dialogView.findViewById(R.id.edtxtBuilding);
        mEdtxtFlat = dialogView.findViewById(R.id.edtxtFlat);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        ArrayAdapter<Region> regionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mRegions);
        ArrayAdapter<City> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCityes);
        ArrayAdapter<Street> streetAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mStreets);

        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnRegion.setAdapter(regionAdapter);
        mSpnCity.setAdapter(cityAdapter);
        mSpnStreet.setAdapter(streetAdapter);

        mTxtError.setVisibility(View.INVISIBLE);
        builder.setView(dialogView).setMessage("Add a new address");

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новый адрес
            String name = mEdtxtName.getText().toString();
            Region region = mRegions.get(mSpnRegion.getSelectedItemPosition());
            City city = mCityes.get(mSpnCity.getSelectedItemPosition());
            Street street = mStreets.get(mSpnStreet.getSelectedItemPosition());
            String home = mEdtxtHome.getText().toString();
            String building = mEdtxtBuilding.getText().toString();
            int flat = Integer.parseInt(mEdtxtFlat.getText().toString());

            if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
            } else {
                Address newAddress = new Address(name, region, city, street, home, building, flat);

                // Получаем ссылку на Fragment
                AddressesFragment callingFragment = (AddressesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("addresses");

                // Передаём newAddress обратно в Fragment
                callingFragment.createNewAddress(newAddress);

                // Закрываем диалог
                dismiss();
            }
        }
    }
}
