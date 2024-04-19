package ru.sknt.vlasovnetwork.vnsr.car.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.RefuelsFragment;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;

public class RefuelsAdapter extends RecyclerView.Adapter<RefuelsAdapter.ListItemHolder> {
    private final List<Refuel> mRefuels;
    private final RefuelsFragment mFragment;

    public RefuelsAdapter(RefuelsFragment fragment, List<Refuel> refuels) {
        mFragment = fragment;
        mRefuels = refuels;
    }

    @NonNull
    @Override
    public RefuelsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_refuel_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelsAdapter.ListItemHolder holder, int position) {
        Refuel refuel = mRefuels.get(position);
        holder.mTxtDateTime.setText(refuel.getDateTime().toString());
        holder.mTxtFuelStation.setText(refuel.getFuelStation().toString());
        holder.mTxtCount.setText(String.valueOf(refuel.getCount()));
        holder.mTxtFuel.setText(refuel.getFuel().getName());
        holder.mTxtCost.setText(String.valueOf(refuel.getCost()));
        holder.mTxtAddress.setText(refuel.getFuelStation().getAddress().getFull());
    }

    @Override
    public int getItemCount() {
        return mRefuels.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtDateTime;
        public TextView mTxtFuelStation;
        public TextView mTxtCount;
        public TextView mTxtFuel;
        public TextView mTxtCost;
        public TextView mTxtAddress;

        public ListItemHolder(View view) {
            super(view);
            mTxtDateTime = view.findViewById(R.id.txtDateTime);
            mTxtFuelStation = view.findViewById(R.id.txtFuelStation);
            mTxtCount = view.findViewById(R.id.txtCount);
            mTxtFuel = view.findViewById(R.id.txtFuel);
            mTxtCost = view.findViewById(R.id.txtCost);
            mTxtAddress = view.findViewById(R.id.txtAddress);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
