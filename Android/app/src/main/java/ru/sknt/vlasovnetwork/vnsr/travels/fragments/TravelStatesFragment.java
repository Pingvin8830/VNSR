package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;
import ru.sknt.vlasovnetwork.vnsr.travels.adapters.TravelStatesAdapter;

public class TravelStatesFragment extends ObjectsFragment {
    private List<TravelState> mTravelStates;
    private TravelStatesAdapter mAdapter;

    @Override
    protected void setObjectList() { mTravelStates = MainActivity.TravelStateDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new TravelStatesAdapter(this, mTravelStates);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mTravelStates.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_travel_states); }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        TravelState travelState = mTravelStates.get(position);
        ShowTravelStateDialog dialog = new ShowTravelStateDialog(travelState);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewTravelStateDialog dialog = new NewTravelStateDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewTravelState(TravelState travelState) {
        MainActivity.TravelStateDao.create(travelState);
        travelState = MainActivity.TravelStateDao.find(travelState.getName()); // Получаем новый корректный Id
        mTravelStates.add(travelState);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteTravelState(TravelState travelState) {
        int position = mTravelStates.indexOf(travelState);
        MainActivity.TravelStateDao.delete(travelState);
        mTravelStates.remove(travelState);
        mAdapter.notifyItemRemoved(position);
        if (mTravelStates.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
