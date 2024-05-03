package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.adapters.TravelsAdapter;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class TravelsFragment extends ObjectsFragment {
    private List<Travel> mTravels;
    private TravelsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mTravels = MainActivity.TravelDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new TravelsAdapter(this, mTravels);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mTravels.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_travels); }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        Travel travel = mTravels.get(position);
        ShowTravelDialog dialog = new ShowTravelDialog(travel);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewTravelDialog dialog = new NewTravelDialog();
        dialog.show(mFragmentManager, "new_travel");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewTravel(Travel travel) {
        MainActivity.TravelDao.create(travel);
        travel = MainActivity.TravelDao.find(travel.getName()); // Получаем новый корректный Id
        mTravels.add(travel);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteTravel(Travel travel) {
        int position = mTravels.indexOf(travel);
        MainActivity.TravelDao.delete(travel);
        mTravels.remove(travel);
        mAdapter.notifyItemRemoved(position);
        if (mTravels.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
