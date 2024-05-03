package ru.sknt.vlasovnetwork.vnsr.travels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.TollRoadsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;

public class TollRoadsAdapter extends RecyclerView.Adapter<TollRoadsAdapter.ListItemHolder> {
    private final List<TollRoad> mTollRoads;
    private final TollRoadsFragment mFragment;

    public TollRoadsAdapter(TollRoadsFragment fragment, List<TollRoad> tollRoads) {
        mFragment = fragment;
        mTollRoads = tollRoads;
    }

    @NonNull
    @Override
    public TollRoadsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_toll_road_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TollRoadsAdapter.ListItemHolder holder, int position) {
        TollRoad tollRoad = mTollRoads.get(position);
        holder.mTxtTravel.setText(tollRoad.getTravel().getName());
        holder.mTxtName.setText(tollRoad.getName());
        holder.mTxtPrice.setText(String.valueOf(tollRoad.getPrice()));
        holder.mTxtStart.setText(tollRoad.getStart());
        holder.mTxtEnd.setText(tollRoad.getEnd());
    }

    @Override
    public int getItemCount() {
        return mTollRoads.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtTravel, mTxtName, mTxtPrice, mTxtStart, mTxtEnd;

        public ListItemHolder(View view) {
            super(view);
            mTxtTravel = view.findViewById(R.id.txtTravel);
            mTxtName = view.findViewById(R.id.txtName);
            mTxtPrice = view.findViewById(R.id.txtPrice);
            mTxtStart = view.findViewById(R.id.txtStart);
            mTxtEnd = view.findViewById(R.id.txtEnd);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
