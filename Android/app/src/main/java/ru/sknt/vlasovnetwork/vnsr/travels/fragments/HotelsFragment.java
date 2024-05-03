package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.ObjectsFragment;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.adapters.HotelsAdapter;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;

public class HotelsFragment extends ObjectsFragment {
    private List<Hotel> mHotels;
    private HotelsAdapter mAdapter;

    @Override
    protected void setObjectList() {
        mHotels = MainActivity.HotelDao.getAll();
    }

    @Override
    protected void setRecyclerViewAdapter() {
        mAdapter = new HotelsAdapter(this, mHotels);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isObjectListEmpty() { return mHotels.isEmpty(); }

    @Override
    protected String getErrorEmptyObjectsText() { return getResources().getString(R.string.err_not_found_hotels); }

    @Override
    protected String getBeforeError() {
        String error = "";
        if (MainActivity.AddressDao.getCount() < 1) { error = getResources().getString(R.string.err_not_found_addresses); }
        return error;
    }

    @Override
    public void showObject(int position) {
        Hotel hotel = mHotels.get(position);
        ShowHotelDialog dialog = new ShowHotelDialog(hotel);
        dialog.show(mFragmentManager, "");
    }

    @Override
    protected void showNewDialog() {
        NewHotelDialog dialog = new NewHotelDialog();
        dialog.show(mFragmentManager, "new_hotel");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewHotel(Hotel hotel) {
        MainActivity.HotelDao.create(hotel);
        hotel = MainActivity.HotelDao.find(hotel.getArrival().getTime(), hotel.getDeparture().getTime()); // Получаем новый корректный Id
        mHotels.add(hotel);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void deleteHotel(Hotel hotel) {
        int position = mHotels.indexOf(hotel);
        MainActivity.HotelDao.delete(hotel);
        mHotels.remove(hotel);
        mAdapter.notifyItemRemoved(position);
        if (mHotels.isEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
