package ru.sknt.vlasovnetwork.vnsr.travels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TravelState;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.TravelStatesFragment;

public class TravelStatesAdapter extends RecyclerView.Adapter<TravelStatesAdapter.ListItemHolder> {
    private final List<TravelState> mTravelStates;
    private final TravelStatesFragment mFragment;

    public TravelStatesAdapter(TravelStatesFragment fragment, List<TravelState> travelStates) {
        mFragment = fragment;
        mTravelStates = travelStates;
    }

    @NonNull
    @Override
    public TravelStatesAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_travel_state_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelStatesAdapter.ListItemHolder holder, int position) {
        TravelState travelState = mTravelStates.get(position);
        holder.mTxtName.setText(travelState.getName());
    }

    @Override
    public int getItemCount() {
        return mTravelStates.size();
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
