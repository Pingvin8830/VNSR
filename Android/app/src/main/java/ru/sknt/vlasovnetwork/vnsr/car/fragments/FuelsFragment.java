package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.adapters.FuelsAdapter;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;

public class FuelsFragment extends ObjectsFragment {
    private List<Fuel> mFuels;
    private FuelsAdapter mAdapter;

    @Override
    protected void setObjectList() { mFuels = MainActivity.FuelDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new FuelsAdapter(this, mFuels);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mFuels.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_fuels); }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        Fuel fuel = mFuels.get(position);
        ShowFuelDialog dialog = new ShowFuelDialog(fuel);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewFuelDialog dialog = new NewFuelDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewFuel(Fuel fuel) {
        MainActivity.FuelDao.create(fuel);
        fuel = MainActivity.FuelDao.find(fuel.getName()); // Получаем новый корректный Id
        mFuels.add(fuel);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteFuel(Fuel fuel) {
        int position = mFuels.indexOf(fuel);
        MainActivity.FuelDao.delete(fuel);
        mFuels.remove(fuel);
        mAdapter.notifyItemRemoved(position);
        if (mFuels.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
