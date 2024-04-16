package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.AddressDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.AddressesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

public class AddressesFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final AddressDao mDao;
    private final RegionDao mRegionDao;
    private final CityDao mCityDao;
    private final CityTypeDao mCityTypeDao;
    private final StreetDao mStreetDao;
    private final StreetTypeDao mStreetTypeDao;
    private List<Address> mAddresses;
    private TextView mTxtError;
    private AddressesAdapter mAdapter;

    public AddressesFragment (AddressDao dao, RegionDao regionDao, CityDao cityDao, CityTypeDao cityTypeDao, StreetDao streetDao, StreetTypeDao streetTypeDao) {
        super();
        mDao = dao;
        mRegionDao = regionDao;
        mCityDao = cityDao;
        mCityTypeDao = cityTypeDao;
        mStreetDao = streetDao;
        mStreetTypeDao = streetTypeDao;
        mAddresses = mDao.getAll();
        for (Address address : mAddresses) {
            address.setRegion(mRegionDao);
            address.setCity(mCityDao, mCityTypeDao);
            address.setStreet(mStreetDao, mStreetTypeDao);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_addresses, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        Button bttnAdd = v.findViewById(R.id.bttnAdd);

        mAdapter = new AddressesAdapter(this, mAddresses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (mAddresses.isEmpty()) {
            mTxtError.setText("Addresses not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewAddressDialog dialog = new NewAddressDialog(mRegionDao, mCityDao, mCityTypeDao, mStreetDao, mStreetTypeDao);
                        dialog.show(mFragmentManager, "");
                    }
                }
        );

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewAddress(Address a) {
        mDao.create(a);
        mAddresses.add(a);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showAddress(int position) {
        Address address = mAddresses.get(position);
        ShowAddressDialog dialog = new ShowAddressDialog(address);
        dialog.show(mFragmentManager, "");
    }

    public void deleteAddress(Address address) {
        int pos = mAddresses.indexOf(address);
        mDao.delete(address);
        mAddresses.remove(address);
        mAdapter.notifyItemRemoved(pos);
        if (mAddresses.isEmpty()) {
            mTxtError.setText("Addresses not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
