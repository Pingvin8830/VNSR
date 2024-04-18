package ru.sknt.vlasovnetwork.vnsr.car.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.FuelStationsFragment;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;

public class FuelStationsAdapter extends RecyclerView.Adapter<FuelStationsAdapter.ListItemHolder> {
    private final List<FuelStation> mFuelStations;
    private final FuelStationsFragment mFragment;

    public FuelStationsAdapter(FuelStationsFragment fragment, List<FuelStation> fuelStations) {
        mFragment = fragment;
        mFuelStations = fuelStations;
    }

    @NonNull
    @Override
    public FuelStationsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_fuel_station_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FuelStationsAdapter.ListItemHolder holder, int position) {
        FuelStation fuelStation = mFuelStations.get(position);
        holder.mTxtName.setText(fuelStation.getName());
    }

    @Override
    public int getItemCount() {
        return mFuelStations.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtName;

        public ListItemHolder(View view) {
            super(view);
            mTxtName = view.findViewById(R.id.txtName);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
