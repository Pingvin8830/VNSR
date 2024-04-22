package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.CityTypesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class CityTypesFragment extends ObjectsFragment {
    private List<CityType> mCityTypes;
    private CityTypesAdapter mAdapter;

    @Override
    protected void setObjectList() { mCityTypes = MainActivity.CityTypeDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new CityTypesAdapter(this, mCityTypes);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mCityTypes.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_city_types); }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        CityType cityType = mCityTypes.get(position);
        ShowCityTypeDialog dialog = new ShowCityTypeDialog(cityType);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewCityTypeDialog dialog = new NewCityTypeDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewCityType(CityType cityType) {
        MainActivity.CityTypeDao.create(cityType);
        cityType = MainActivity.CityTypeDao.find(cityType.getName()); // Получаем новый корректный Id
        mCityTypes.add(cityType);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteCityType(CityType cityType) {
        int position = mCityTypes.indexOf(cityType);
        MainActivity.CityTypeDao.delete(cityType);
        mCityTypes.remove(cityType);
        mAdapter.notifyItemRemoved(position);
        if (mCityTypes.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}