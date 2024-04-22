package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.adapters.RefuelsAdapter;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;

public class RefuelsFragment extends ObjectsFragment {
    private List<Refuel> mRefuels;
    private RefuelsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mRefuels = MainActivity.RefuelDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new RefuelsAdapter(this, mRefuels);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mRefuels.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_refuels); }

    @Override
    protected String getBeforeError() {
        String error = "";
        if      (MainActivity.FuelStationDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_fuel_stations); }
        else if (MainActivity.FuelDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_fuels); }
        return error;
    }

    @Override
    public void showObject(int position) {
        Refuel refuel = mRefuels.get(position);
        ShowRefuelDialog dialog = new ShowRefuelDialog(refuel);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewRefuelDialog dialog = new NewRefuelDialog();
        dialog.show(mFragmentManager, "new_refuel");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewRefuel(Refuel refuel) {
        MainActivity.RefuelDao.create(refuel);
        refuel = MainActivity.RefuelDao.find(refuel.getDateTime()); // Получаем новый корректный Id
        mRefuels.add(refuel);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteRefuel(Refuel refuel) {
        int position = mRefuels.indexOf(refuel);
        MainActivity.RefuelDao.delete(refuel);
        mRefuels.remove(refuel);
        mAdapter.notifyItemRemoved(position);
        if (mRefuels.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
