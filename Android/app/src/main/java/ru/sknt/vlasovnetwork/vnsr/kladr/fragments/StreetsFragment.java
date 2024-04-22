package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.StreetsAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class StreetsFragment extends ObjectsFragment {
    private List<Street> mStreets;
    private StreetsAdapter mAdapter;

    @Override
    protected void setObjectList() { mStreets = MainActivity.StreetDao.getAll(); }

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
        return getResources().getString(R.string.err_not_found_streets);
    }

    @Override
    protected String getBeforeError() {
        String error = "";
        if (MainActivity.StreetTypeDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_street_types); }
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