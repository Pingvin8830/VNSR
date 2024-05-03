package ru.sknt.vlasovnetwork.vnsr.travels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.WaysFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;

public class WaysAdapter extends RecyclerView.Adapter<WaysAdapter.ListItemHolder> {
    private final List<Way> mWays;
    private final WaysFragment mFragment;

    public WaysAdapter(WaysFragment fragment, List<Way> ways) {
        mFragment = fragment;
        mWays = ways;
    }

    @NonNull
    @Override
    public WaysAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.travels_way_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WaysAdapter.ListItemHolder holder, int position) {
        Way way = mWays.get(position);
        holder.mTxtTravel.setText(way.getTravel().getName());
        holder.mTxtStartPoint.setText(way.getStartPoint().getAddress().getName());
        holder.mTxtTargetPoint.setText(way.getTargetPoint().getAddress().getName());
    }

    @Override
    public int getItemCount() {
        return mWays.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtTravel;
        public TextView mTxtStartPoint;
        public TextView mTxtTargetPoint;

        public ListItemHolder(View view) {
            super(view);
            mTxtTravel = view.findViewById(R.id.txtTravel);
            mTxtStartPoint = view.findViewById(R.id.txtStartPoint);
            mTxtTargetPoint = view.findViewById(R.id.txtTargetPoint);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
