package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.StreetsAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class StreetsFragment extends ObjectsFragment {
    private List<Street> mStreets;
    private StreetsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mStreets = MainActivity.StreetDao.getAll();
        for (Street street : mStreets) { street.setStreetType(); }
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new StreetsAdapter(this, mStreets);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() {
        return mStreets.isEmpty();
    }

    @Override
    protected String getErrorEmptyObjectsText() {
        return "Streets not found";
    }

    @Override
    protected String getBeforeError() {
        String error = "";
        if (MainActivity.StreetTypeDao.getCount() < 1) { error = "Street types not fonud"; }
        return error;
    }

    @Override
    public void showObject(int position) {
        Street street = mStreets.get(position);
        ShowStreetDialog dialog = new ShowStreetDialog(street);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewStreetDialog dialog = new NewStreetDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewStreet(Street street) {
        MainActivity.StreetDao.create(street);
        street = MainActivity.StreetDao.find(street.getStreetType().getId(), street.getName()); // Получаем новый корректный Id
        street.setStreetType(); // Устанавливаем Foreign
        mStreets.add(street);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteStreet(Street street) {
        int position = mStreets.indexOf(street);
        MainActivity.StreetDao.delete(street);
        mStreets.remove(street);
        mAdapter.notifyItemRemoved(position);
        if (mStreets.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}