package ru.sknt.vlasovnetwork.vnsr.travels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.HotelsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ListItemHolder> {
    private final List<Hotel> mHotels;
    private final HotelsFragment mFragment;

    public HotelsAdapter(HotelsFragment fragment, List<Hotel> hotels) {
        mFragment = fragment;
        mHotels = hotels;
    }

    @NonNull
    @Override
    public HotelsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_hotel_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelsAdapter.ListItemHolder holder, int position) {
        Hotel hotel = mHotels.get(position);
        holder.mTxtName.setText(hotel.getName());
        holder.mTxtCost.setText(String.valueOf(hotel.getCost()));
        holder.mTxtArrivalDateTime.setText("-> " + hotel.getArrival().toString());
        holder.mTxtDepartureDateTime.setText(hotel.getDeparture().toString() + " ->");
    }

    @Override
    public int getItemCount() {
        return mHotels.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtName, mTxtCost, mTxtArrivalDateTime, mTxtDepartureDateTime;

        public ListItemHolder(View view) {
            super(view);
            mTxtName = view.findViewById(R.id.txtName);
            mTxtCost = view.findViewById(R.id.txtCost);
            mTxtArrivalDateTime = view.findViewById(R.id.txtArrivalDateTime);
            mTxtDepartureDateTime = view.findViewById(R.id.txtDepartureDateTime);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
