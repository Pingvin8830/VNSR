package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.StreetTypesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class StreetTypesFragment extends ObjectsFragment {
    private List<StreetType> mStreetTypes;
    private  StreetTypesAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mStreetTypes = MainActivity.StreetTypeDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new StreetTypesAdapter(this, mStreetTypes);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() {
        return mStreetTypes.isEmpty();
    }

    @Override
    protected String getErrorEmptyObjectsText() {
        return getResources().getString(R.string.err_not_found_street_types);
    }

    @Override
    protected String getBeforeError() { return ""; }

    @Override
    public void showObject(int position) {
        StreetType streetType = mStreetTypes.get(position);
        ShowStreetTypeDialog dialog = new ShowStreetTypeDialog(streetType);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewStreetTypeDialog dialog = new NewStreetTypeDialog();
        dialog.show(mFragmentManager, "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewStreetType(StreetType streetType) {
        MainActivity.StreetTypeDao.create(streetType);
        streetType = MainActivity.StreetTypeDao.find(streetType.getName()); // Получаем новый корректный Id
        mStreetTypes.add(streetType);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteStreetType(StreetType streetType) {
        int position = mStreetTypes.indexOf(streetType);
        MainActivity.StreetTypeDao.delete(streetType);
        mStreetTypes.remove(streetType);
        mAdapter.notifyItemRemoved(position);
        if (mStreetTypes.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}