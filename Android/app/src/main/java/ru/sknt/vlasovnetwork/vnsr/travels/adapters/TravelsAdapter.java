package ru.sknt.vlasovnetwork.vnsr.travels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.TravelsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.ListItemHolder> {
    private final List<Travel> mTravels;
    private final TravelsFragment mFragment;

    public TravelsAdapter(TravelsFragment fragment, List<Travel> travels) {
        mFragment = fragment;
        mTravels = travels;
    }

    @NonNull
    @Override
    public TravelsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_travel_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelsAdapter.ListItemHolder holder, int position) {
        Travel travel = mTravels.get(position);
        holder.mTxtName.setText(travel.getName());
        try { holder.mTxtStart.setText(travel.getStartDateTime().toString()); }
        catch (NullPointerException e) { holder.mTxtStart.setText(R.string.lbl_unknown); }
        try { holder.mTxtEnd.setText(travel.getEndDateTime().toString()); }
        catch (NullPointerException e) { holder.mTxtEnd.setText(R.string.lbl_unknown); }
        holder.mTxtDistance.setText(String.valueOf(travel.getDistance()));
        holder.mTxtFuelCount.setText(String.valueOf(travel.getFuelCount()));
        holder.mTxtPoints.setText(String.valueOf(travel.getPoints().size()));
        holder.mTxtFuelConsumption.setText(String.valueOf(travel.getFuelConsumption()));
    }

    @Override
    public int getItemCount() {
        return mTravels.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtName;
        public TextView mTxtStart;
        public TextView mTxtEnd;
        public TextView mTxtDistance;
        public TextView mTxtFuelCount;
        public TextView mTxtPoints;
        public TextView mTxtFuelConsumption;

        public ListItemHolder(View view) {
            super(view);
            mTxtName = view.findViewById(R.id.txtName);
            mTxtStart = view.findViewById(R.id.txtStart);
            mTxtEnd = view.findViewById(R.id.txtEnd);
            mTxtDistance = view.findViewById(R.id.txtDistance);
            mTxtFuelCount = view.findViewById(R.id.txtFuelCount);
            mTxtPoints = view.findViewById(R.id.txtPoints);
            mTxtFuelConsumption = view.findViewById(R.id.txtFuelConsumption);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
