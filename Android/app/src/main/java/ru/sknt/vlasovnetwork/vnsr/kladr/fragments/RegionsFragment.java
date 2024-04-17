package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.RegionsAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class RegionsFragment extends ObjectsFragment {
    private List<Region> mRegions;
    private RegionsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mRegions = MainActivity.RegionDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new RegionsAdapter(this, mRegions);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() {
        return mRegions.isEmpty();
    }

    @Override
    protected String getErrorEmptyObjectsText() {
        return "Regions not found";
    }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        Region region = mRegions.get(position);
        ShowRegionDialog dialog = new ShowRegionDialog(region);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewRegionDialog dialog = new NewRegionDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewRegion(Region region) {
        MainActivity.RegionDao.create(region);
        region = MainActivity.RegionDao.find(region.getCode()); // Получаем новый корректный Id
        mRegions.add(region);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteRegion(Region region) {
        int position = mRegions.indexOf(region);
        MainActivity.RegionDao.delete(region);
        mRegions.remove(region);
        mAdapter.notifyItemRemoved(position);
        if (mRegions.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }

}