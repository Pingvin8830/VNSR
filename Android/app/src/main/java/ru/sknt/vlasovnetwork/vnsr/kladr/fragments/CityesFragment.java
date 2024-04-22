package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.CityesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;

public class CityesFragment extends ObjectsFragment {
    private List<City> mCityes;
    private CityesAdapter mAdapter;

    @Override
    protected void setObjectList() { mCityes = MainActivity.CityDao.getAll(); }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new CityesAdapter(this, mCityes);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mCityes.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_cityes); }

    @Override
    protected String getBeforeError() {
        String error = "";
        if (MainActivity.CityTypeDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_city_types); }
        return error;
    }

    @Override
    public void showObject(int position) {
        City city = mCityes.get(position);
        ShowCityDialog dialog = new ShowCityDialog(city);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewCityDialog dialog = new NewCityDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewCity(City city) {
        MainActivity.CityDao.create(city);
        city = MainActivity.CityDao.find(city.getName()); // Получаем новый корректный Id
        mCityes.add(city);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteCity(City city) {
        int position = mCityes.indexOf(city);
        MainActivity.CityDao.delete(city);
        mCityes.remove(city);
        mAdapter.notifyItemRemoved(position);
        if (mCityes.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}