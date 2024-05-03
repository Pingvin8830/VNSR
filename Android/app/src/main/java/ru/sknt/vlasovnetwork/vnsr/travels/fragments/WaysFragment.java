package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.adapters.WaysAdapter;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;

public class WaysFragment extends ObjectsFragment {
    private List<Way> mWays;
    private WaysAdapter mAdapter;

    @Override
    protected void setObjectList() { mWays = MainActivity.WayDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new WaysAdapter(this, mWays);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mWays.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_ways); }

    @Override
    protected String getBeforeError() {
        String error = "";
        if      (MainActivity.PointDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_points); }
        return error;
    }

    @Override
    public void showObject(int position) {
        Way way = mWays.get(position);
        ShowWayDialog dialog = new ShowWayDialog(way);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewWayDialog dialog = new NewWayDialog();
        dialog.show(mFragmentManager, "new_way");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewWay(Way way) {
        MainActivity.WayDao.create(way);
        way = MainActivity.WayDao.find(way.getTravel().getName(), way.getStartPoint().getAddress().getName(), way.getTargetPoint().getAddress().getName()); // Получаем новый корректный Id
        mWays.add(way);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteWay(Way way) {
        int position = mWays.indexOf(way);
        MainActivity.WayDao.delete(way);
        mWays.remove(way);
        mAdapter.notifyItemRemoved(position);
        if (mWays.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
