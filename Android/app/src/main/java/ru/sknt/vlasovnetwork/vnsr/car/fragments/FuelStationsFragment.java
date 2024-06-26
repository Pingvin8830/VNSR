package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.adapters.FuelStationsAdapter;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;

public class FuelStationsFragment extends ObjectsFragment {
    private List<FuelStation> mFuelStations;
    private FuelStationsAdapter mAdapter;

    @Override
    protected void setObjectList() { mFuelStations = MainActivity.FuelStationDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new FuelStationsAdapter(this, mFuelStations);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mFuelStations.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_fuel_stations); }

    @Override
    protected String getBeforeError() {
        String error = "";
        if      (MainActivity.AddressDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_addresses); }
        return error;
    }

    @Override
    public void showObject(int position) {
        FuelStation fuelStation = mFuelStations.get(position);
        ShowFuelStationDialog dialog = new ShowFuelStationDialog(fuelStation);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewFuelStationDialog dialog = new NewFuelStationDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewFuelStation(FuelStation fuelStation) {
        MainActivity.FuelStationDao.create(fuelStation);
        fuelStation = MainActivity.FuelStationDao.find(fuelStation.getCompany(), fuelStation.getNumber());
        mFuelStations.add(fuelStation);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteFuelStation(FuelStation fuelStation) {
        int position = mFuelStations.indexOf(fuelStation);
        MainActivity.FuelStationDao.delete(fuelStation);
        mFuelStations.remove(fuelStation);
        mAdapter.notifyItemRemoved(position);
        if (mFuelStations.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
