package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.adapters.PointsAdapter;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;

public class PointsFragment extends ObjectsFragment {
    private List<Point> mPoints;
    private PointsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mPoints = MainActivity.PointDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new PointsAdapter(this, mPoints);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mPoints.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_points); }

    @Override
    protected String getBeforeError() {
        String error = "";
        if      (MainActivity.TravelDao.getCount() < 1)  { error = getResources().getString(R.string.err_not_found_travels); }
        else if (MainActivity.AddressDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_addresses); }
        return error;
    }

    @Override
    public void showObject(int position) {
        Point point = mPoints.get(position);
        ShowPointDialog dialog = new ShowPointDialog(point);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewPointDialog dialog = new NewPointDialog();
        dialog.show(mFragmentManager, "new_point");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewPoint(Point point) {
        MainActivity.PointDao.create(point);
        point = MainActivity.PointDao.find(point.getDateTime().getTime()); // Получаем новый корректный Id
        mPoints.add(point);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deletePoint(Point point) {
        int position = mPoints.indexOf(point);
        MainActivity.PointDao.delete(point);
        mPoints.remove(point);
        mAdapter.notifyItemRemoved(position);
        if (mPoints.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
