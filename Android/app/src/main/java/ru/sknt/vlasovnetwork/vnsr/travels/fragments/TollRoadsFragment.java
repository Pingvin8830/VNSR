package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.adapters.TollRoadsAdapter;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;

public class TollRoadsFragment extends ObjectsFragment {
    private List<TollRoad> mTollRoads;
    private TollRoadsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mTollRoads = MainActivity.TollRoadDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new TollRoadsAdapter(this, mTollRoads);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mTollRoads.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_toll_roads); }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        TollRoad tollRoad = mTollRoads.get(position);
        ShowTollRoadDialog dialog = new ShowTollRoadDialog(tollRoad);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewTollRoadDialog dialog = new NewTollRoadDialog();
        dialog.show(mFragmentManager, "new_toll_road");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewTollRoad(TollRoad tollRoad) {
        MainActivity.TollRoadDao.create(tollRoad);
        tollRoad = MainActivity.TollRoadDao.find(tollRoad.getTravel().getName(), tollRoad.getStart(), tollRoad.getEnd()); // Получаем новый корректный Id
        mTollRoads.add(tollRoad);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteTollRoad(TollRoad tollRoad) {
        int position = mTollRoads.indexOf(tollRoad);
        MainActivity.TollRoadDao.delete(tollRoad);
        mTollRoads.remove(tollRoad);
        mAdapter.notifyItemRemoved(position);
        if (mTollRoads.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
