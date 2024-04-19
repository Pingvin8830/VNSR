package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.AddressesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

public class AddressesFragment extends ObjectsFragment {
    private List<Address> mAddresses;
    private AddressesAdapter mAdapter;

    @Override
    protected void setObjectList() { mAddresses = MainActivity.AddressDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new AddressesAdapter(this, mAddresses);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mAddresses.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return "Addresses not found"; }

    @Override
    protected String getBeforeError() {
        String error = "";
        if      (MainActivity.RegionDao.getCount() < 1) { error = "Regions not found"; }
        else if (MainActivity.CityDao.getCount()   < 1) { error = "Cityes not found"; }
        else if (MainActivity.StreetDao.getCount() < 1) { error = "Streets not found"; }
        return error;
    }

    @Override
    public void showObject(int position) {
        Address address = mAddresses.get(position);
        ShowAddressDialog dialog = new ShowAddressDialog(address);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewAddressDialog dialog = new NewAddressDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewAddress(Address address) {
        MainActivity.AddressDao.create(address);
        address = MainActivity.AddressDao.find(address.getName()); // Получаем новый корректный Id
        mAddresses.add(address);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteAddress(Address address) {
        int position = mAddresses.indexOf(address);
        MainActivity.AddressDao.delete(address);
        mAddresses.remove(address);
        mAdapter.notifyItemRemoved(position);
        if (mAddresses.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
