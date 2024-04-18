package ru.sknt.vlasovnetwork.vnsr.car.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.FuelsFragment;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;

public class FuelsAdapter extends RecyclerView.Adapter<FuelsAdapter.ListItemHolder> {
    private final List<Fuel> mFuels;
    private final FuelsFragment mFragment;

    public FuelsAdapter(FuelsFragment fragment, List<Fuel> fuels) {
        mFragment = fragment;
        mFuels = fuels;
    }

    @NonNull
    @Override
    public FuelsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_fuel_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FuelsAdapter.ListItemHolder holder, int position) {
        Fuel fuel = mFuels.get(position);
        holder.mTxtName.setText(fuel.getName());
        holder.mTxtFuelStation.setText(fuel.getFuelStation().getName());
    }

    @Override
    public int getItemCount() {
        return mFuels.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtName;
        public TextView mTxtFuelStation;

        public ListItemHolder(View view) {
            super(view);
            mTxtName = view.findViewById(R.id.txtName);
            mTxtFuelStation = view.findViewById(R.id.txtFuelStation);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
